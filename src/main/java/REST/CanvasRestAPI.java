package REST;

import DB.CanvasDB;
import io.javalin.Javalin;

public class CanvasRestAPI {

    public static void main(String[] args) {

        Javalin app = Javalin.create()
                .start(7000);

        // Get all courses
        app.get("/canvas/courses", ctx -> {
            ctx.json(CanvasDB.getCourses());
        });

        // Get assignments for course
        app.get("/canvas/assignments/:courseCode", ctx -> {

            String courseCode =
                    ctx.param("courseCode");

            ctx.json(
                    CanvasDB.getAssignments(courseCode)
            );
        });

        // Get students for assignment
        app.get("/canvas/students/:examType", ctx -> {

            String examType =
                    ctx.param("examType");

            ctx.json(
                    CanvasDB.getStudents(examType)
            );
        });

        // Get grades for assignment
        app.get("/canvas/grades/:examType", ctx -> {

            String examType =
                    ctx.param("examType");

            ctx.json(
                    CanvasDB.getGrades(examType)
            );
        });
    }
}