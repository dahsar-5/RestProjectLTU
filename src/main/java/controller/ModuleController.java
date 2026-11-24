
package controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

// Retrieves modules from E-pok based on a course code
// and verifies that the modules also exist in Ladok

 public class ModuleController {

    public static List<String> getModules(String courseCode) throws Exception {

        HttpClient client = HttpClient.newHttpClient();
        ObjectMapper mapper = new ObjectMapper();

        // 1. Get modules for the selected course from E-pok
        String epokUrl = "http://localhost:7002/epok/modules/" + courseCode;

        HttpRequest epokRequest = HttpRequest.newBuilder()
                .uri(URI.create(epokUrl))
                .GET()
                .build();

        HttpResponse<String> epokResponse = client.send(
                epokRequest,
                HttpResponse.BodyHandlers.ofString()
        );

        if (epokResponse.statusCode() != 200) {
            throw new RuntimeException(
                    "E-pok request failed: " + epokResponse.statusCode()
            );
        }

        List<String> epokModules = mapper.readValue(
                epokResponse.body(),
                new TypeReference<List<String>>() {}
        );

        // 2. Check which modules also exist in Ladok
        List<String> matchedModules = new ArrayList<>();

        for (String moduleCode : epokModules) {

            String ladokUrl =
                    "http://localhost:7001/ladok/modules/" + moduleCode;

            HttpRequest ladokRequest = HttpRequest.newBuilder()
                    .uri(URI.create(ladokUrl))
                    .GET()
                    .build();

            HttpResponse<String> ladokResponse = client.send(
                    ladokRequest,
                    HttpResponse.BodyHandlers.ofString()
            );

            if (ladokResponse.statusCode() == 200) {
                matchedModules.add(moduleCode);
            } else if (ladokResponse.statusCode() != 404) {
                throw new RuntimeException(
                        "Ladok request failed: " + ladokResponse.statusCode()
                );
            }
        }

        // 3. Return only modules that exist in Ladok
        return matchedModules;
    }

}
