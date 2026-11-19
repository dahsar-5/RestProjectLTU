package REST;

import io.javalin.Javalin;

public class LadokRestAPI {

    public static void main(String[] args) {

        Javalin app = Javalin.create()
                .port(7000)
                .start();
        app.post("/ladok/result", ctx -> {

            String personnummer = ctx.formParam("personnummer");
            String courseCode = ctx.formParam("courseCode");
            String moduleCode = ctx.formParam("moduleCode");
            String examDate = ctx.formParam("examDate");
            String grade = ctx.formParam("grade");

        });





    }
}