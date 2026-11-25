package controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import model.ModuleController;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class Controller {

    @FXML
    private ComboBox<String> CourceCode;

    @FXML
    private ComboBox<String> canvasAssignment;

    @FXML
    private ComboBox<String> ladokModule;

    @FXML
    public void initialize() {

        System.out.println("INITIALIZE RUNNING");

        try {
            loadCourses();
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
}
