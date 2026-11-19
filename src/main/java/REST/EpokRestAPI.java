
package REST;

import DB.EpokDB;
import io.javalin.Javalin;

public class EpokRestAPI {

    public static void main(String[] args) {

        Javalin app = Javalin.create()
                .port(7000)
                .start();

        // Returns all courses from E-pok
        app.get("/epok/courses", ctx -> {
            ctx.json(EpokDB.getCourses());
        });

        // Returns modules for a selected course
        app.get("/epok/modules/:courseCode", ctx -> {
            String courseCode = ctx.param("courseCode");
            ctx.json(EpokDB.getModules(courseCode));
        });
    }
}
