package databaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ITSAdminConnection {
    private static final String URL =
            "jdbc:postgresql://localhost:5432/ITSAdminDB";

    private static final String USER = "postgres";

    private static final String PASSWORD =
            System.getenv("POSTGRES_PASSWORD");

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
