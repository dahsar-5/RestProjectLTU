package REST;

import DB.CanvasDB;
import io.javalin.Javalin;

public class CanvasRestAPI {

    public static void main(String[] args) {

        Javalin app = Javalin.create()
                .start(7000);

        app.get("/canvas/courses", ctx -> {
            ctx.json(CanvasDB.getCourses());
        });

        app.get("/canvas/assignments/:courseCode", ctx -> {

            String courseCode = ctx.param("courseCode");

            ctx.json(
                    CanvasDB.getAssignments(courseCode)
            );
        });

        app.get("/canvas/students/:examType", ctx -> {

            String examType = ctx.param("examType");

            ctx.json(
                    CanvasDB.getStudents(examType)
            );
        });

        app.get("/canvas/grades/:examType", ctx -> {

            String examType = ctx.param("examType");

            ctx.json(
                    CanvasDB.getGrades(examType)
            );
        });
    }
}

        return results;
    }

}
