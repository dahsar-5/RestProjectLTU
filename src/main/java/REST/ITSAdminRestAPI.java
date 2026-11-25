package REST;


import DB.ITSAdminDB;
import io.javalin.Javalin;

public class ITSAdminRestAPI {
    public static void main(String[] args) {

        Javalin app = Javalin.create()
                .port(7003)
                .start();

    }
}