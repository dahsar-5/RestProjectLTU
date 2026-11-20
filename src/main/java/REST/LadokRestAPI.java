
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
