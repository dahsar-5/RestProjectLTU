
package REST;

import DB.LadokDB;
import io.javalin.Javalin;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.Map;

public class LadokRestAPI {

    public static void main(String[] args) {

        Javalin app = Javalin.create()
                .port(7001) //Changed the port, so we use different for different REST services
                .start();

        //POST endpoint
        app.post("/ladok/result", ctx -> {

            // Read JSON request body
            JsonNode body = ctx.bodyAsClass(JsonNode.class);

            String personnummer = body.get("personnummer").asText();
            String courseCode = body.get("courseCode").asText();
            String moduleCode = body.get("moduleCode").asText();
            String examDate = body.get("examDate").asText();
            String grade = body.get("grade").asText();

            // Check if the student exists in Ladok
            if (!LadokDB.studentExists(personnummer)) {
                ctx.status(404);
                ctx.json(Map.of("message", "Student not found in Ladok"));
                return;
            }

            // Find Ladok module ID using the moduleCode
            int code = Integer.parseInt(moduleCode);
            int modulID = LadokDB.findModulID(code);

            // Check if module was found
            if (modulID == 0) {
                ctx.status(404);
                ctx.json(Map.of("message", "Module not found in Ladok"));
                return;
            }

            // Check if result already exist
            if (LadokDB.resultExists(personnummer, modulID)) {
                ctx.status(409);
                ctx.json(Map.of("message", "Result already exists in Ladok"));
                return;
            }

            // Register the result in Ladok
            LadokDB.insertResult(personnummer, modulID, examDate, grade);

            ctx.status(201);
            ctx.json(Map.of("message", "Result registered successfully in Ladok"));
        });


           // Find Ladok's internal module ID based on a module code
        app.get("/ladok/modules/:moduleCode", ctx -> {

            int moduleCode = Integer.parseInt(ctx.param("moduleCode"));

            int modulID = LadokDB.findModulID(moduleCode);

            if (modulID != 0) {
                ctx.json(modulID);
            } else {
                ctx.status(404);
                ctx.json(Map.of("message", "Module not found in Ladok"));
            }


        });


        // Get results from Ladok based on module code
        app.get("/ladok/results/:moduleCode", ctx -> {

            int moduleCode =
                    Integer.parseInt(ctx.param("moduleCode"));

            ctx.json(LadokDB.getResultsByModule(moduleCode));

        });
    }
}
