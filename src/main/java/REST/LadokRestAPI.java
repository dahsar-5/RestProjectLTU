
package REST;

import DB.LadokDB;
import io.javalin.Javalin;

public class LadokRestAPI {

    public static void main(String[] args) {

        Javalin app = Javalin.create()
                .port(7001) //Changed the port, so we use different for different REST services
                .start();

        //POST endpoint
        app.post("/ladok/result", ctx -> {

            String personnummer = ctx.formParam("personnummer");
            String courseCode = ctx.formParam("courseCode");
            String moduleCode = ctx.formParam("moduleCode");
            String examDate = ctx.formParam("examDate");
            String grade = ctx.formParam("grade");

            // Check if the student exists in Ladok
            if (!LadokDB.studentExists(personnummer)) {
                ctx.status(404);
                ctx.result("Student not found in Ladok");
                return;
            }

            // Find Ladok module ID using the moduleCode
            int code = Integer.parseInt(moduleCode);
            int modulID = LadokDB.findModulID(code);

            // Check if module was found
            if (modulID == 0) {
                ctx.status(404);
                ctx.result("Module not found in Ladok");
                return;
            }

            // Check if result already exist
            if (LadokDB.resultExists(personnummer, modulID)) {
                ctx.status(409);
                ctx.result("Result already exists in Ladok");
                return;
            }

            // Register the result in Ladok
            LadokDB.insertResult(personnummer, modulID, examDate, grade);

            ctx.status(201);
            ctx.result("Result registered successfully in Ladok");
        });



           // Find Ladok's internal module ID based on a module code
        app.get("/ladok/modules/:moduleCode", ctx -> {

            int moduleCode = Integer.parseInt(ctx.param("moduleCode"));

            int modulID = LadokDB.findModulID(moduleCode);

            if (modulID != 0) {
                ctx.json(modulID);
            } else {
                ctx.status(404);
                ctx.result("Module not found in Ladok");
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
