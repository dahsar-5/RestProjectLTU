package model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;


public class ResultController {

    public static void loadLadokResults(
            String moduleCode,
            List<StudentTeacher> students
    ) throws Exception {

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(
                        "http://localhost:7001/ladok/results/"
                                + moduleCode
                ))
                .GET()
                .build();

        HttpResponse<String> response = client.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );

        if (response.statusCode() != 200) {
            throw new Exception("Failed to fetch Ladok results");
        }

        ObjectMapper mapper = new ObjectMapper();

        List<Result> results = mapper.readValue(
                response.body(),
                new TypeReference<List<Result>>() {}
        );

        // Match Ladok results with students
        for (StudentTeacher student : students) {

            // Reset previous values
            student.setLadokGrade(null);
            student.setExaminationDate(null);
            student.setRegistered(false);

            for (Result result : results) {

                if (result.getPersonnr().equals(student.getPersonnr())) {

                    student.setLadokGrade(result.getFinalGrade());

                    if (result.getExamDate() != null) {
                        student.setExaminationDate(
                                LocalDate.parse(result.getExamDate())
                        );
                    }

                    student.setRegistered(result.isRegistered());

                    break;
                }
            }
        }
    }

    // Transfer a student result to Ladok
    public static boolean transferResult(
            StudentTeacher student,
            String courseCode,
            String moduleCode
    ) throws Exception {

        // Create request data
        Map<String, String> resultData = Map.of(
                "personnummer", student.getPersonnr(),
                "courseCode", courseCode,
                "moduleCode", moduleCode,
                "examDate", student.getExaminationDate().toString(),
                "grade", student.getLadokGrade()
        );

        // Convert Java data to JSON
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(resultData);

        // Create HTTP POST request
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:7001/ladok/result"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        // Send request to Ladok
        HttpResponse<String> response = client.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );

        System.out.println(response.statusCode());
        System.out.println(response.body());

        return response.statusCode() == 201;
    }
}