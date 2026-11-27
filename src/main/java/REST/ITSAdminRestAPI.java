package REST;

import DB.ITSAdminDB;
import io.javalin.Javalin;
import model.StudentTeacher;

public class ITSAdminRestAPI {

    public static void main(String[] args) {

        Javalin app = Javalin.create()
                .port(7003)
                .start();

        // Get all students/teachers
        app.get("/itsadmin/users", ctx -> {
            ctx.json(ITSAdminDB.getAllUsers());
        });

        // Get single user by studentId (e.g., /itsadmin/users/pareli-1)
        app.get("/itsadmin/users/:studentId", ctx -> {
            String studentId = ctx.param("studentId");
            StudentTeacher user = ITSAdminDB.getUserByStudentId(studentId);
            if (user == null) {
                ctx.status(404).result("User not found");
            } else {
                ctx.json(user);
            }
        });

        // Create user from query/form params or JSON body
        app.post("/itsadmin/users", ctx -> {
            StudentTeacher incoming = ctx.bodyAsClass(StudentTeacher.class);
            String password = ctx.queryParam("password");
            if (password == null || password.isBlank()) {
                password = "changeme";
            }

            boolean created = ITSAdminDB.createUser(
                    incoming.getPersonnr(),
                    incoming.getStudentId(),
                    incoming.getfName(),
                    incoming.getlName(),
                    incoming.getEmail(),
                    password,
                    incoming.getStatus()
            );

            ctx.status(created ? 201 : 400).result(created ? "Created" : "Not created");
        });
    }
}