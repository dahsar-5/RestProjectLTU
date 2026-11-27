package controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.ModuleController;
import model.StudentTeacher;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class Controller {

    @FXML
    private TableView<StudentTeacher> dispalyTable;

    @FXML
    private TableColumn<StudentTeacher, String> nameCol;

    @FXML
    private ComboBox<String> CourceCode;

    @FXML
    private ComboBox<String> canvasAssignment;

    @FXML
    private ComboBox<String> ladokModule;

    @FXML
    public void initialize() {

        System.out.println("INITIALIZE RUNNING");

        nameCol.setCellValueFactory(cellData -> {
            StudentTeacher u = cellData.getValue();
            String first = u.getfName() != null ? u.getfName() : "";
            String last = u.getlName() != null ? u.getlName() : "";
            return new SimpleStringProperty((first + " " + last).trim());
        });

        try {
            loadCourses();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            List<StudentTeacher> users = loadUsers();
            for (StudentTeacher user : users) {
                System.out.println(user.getStudentId() + " - " + user.getfName() + " " + user.getlName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        CourceCode.setOnAction(event -> {

            String courseCode = CourceCode.getValue();

            if (courseCode != null) {

                // Load assignments from Canvas
                try {
                    loadAssignments(courseCode);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // Load verified modules from E-pok and Ladok
                try {
                    ladokModule.getItems().clear();

                    List<String> modules =
                            ModuleController.getModules(courseCode);

                    ladokModule.getItems().addAll(modules);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                // Load students into dispalyTable
                try {
                    loadStudentsForCourse(courseCode);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void loadCourses() throws Exception {

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request =
                HttpRequest.newBuilder()
                        .uri(URI.create("http://localhost:7000/canvas/courses"))
                        .GET()
                        .build();

        HttpResponse<String> response =
                client.send(
                        request,
                        HttpResponse.BodyHandlers.ofString()
                );

        System.out.println("Courses response:");
        System.out.println(response.body());

        ObjectMapper mapper = new ObjectMapper();

        List<String> courses =
                mapper.readValue(
                        response.body(),
                        new TypeReference<List<String>>() {}
                );

        CourceCode.getItems().clear();
        CourceCode.getItems().addAll(courses);
    }

    private void loadAssignments(String courseCode) throws Exception {

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request =
                HttpRequest.newBuilder()
                        .uri(
                                URI.create(
                                        "http://localhost:7000/canvas/assignments/"
                                                + courseCode
                                )
                        )
                        .GET()
                        .build();

        HttpResponse<String> response =
                client.send(
                        request,
                        HttpResponse.BodyHandlers.ofString()
                );

        System.out.println("Assignments response:");
        System.out.println(response.body());

        ObjectMapper mapper = new ObjectMapper();

        List<String> assignments =
                mapper.readValue(
                        response.body(),
                        new TypeReference<List<String>>() {}
                );

        canvasAssignment.getItems().clear();
        canvasAssignment.getItems().addAll(assignments);
    }

    private List<StudentTeacher> loadUsers() throws Exception {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:7003/itsadmin/users"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );

        System.out.println("Users status: " + response.statusCode());
        System.out.println("Users response:");
        System.out.println(response.body());

        if (response.statusCode() != 200 || response.body() == null || response.body().isBlank()) {
            return List.of();
        }

        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(
                response.body(),
                new TypeReference<List<StudentTeacher>>() {}
        );
    }

    private void loadStudentsForCourse(String courseCode) throws Exception {
        if (courseCode == null || courseCode.isBlank()) {
            dispalyTable.getItems().clear();
            return;
        }

        List<StudentTeacher> users = loadUsers();
        List<StudentTeacher> students = users.stream()
                .filter(u -> "Student".equalsIgnoreCase(u.getStatus()))
                .toList();

        dispalyTable.getItems().setAll(students);
    }
}
