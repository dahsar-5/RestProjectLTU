
package DB;

import databaseConnection.EpokConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EpokDB {

    // Gets a full list of all courses
    public static List<String> getCourses() {

        List<String> courses = new ArrayList<>();

        String sql = "SELECT CourseCode FROM course";

        try (
                Connection conn = EpokConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()
        ) {

            while (rs.next()) {
                courses.add(rs.getString("CourseCode"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return courses;
    }

    // Gets all active modules for a specific course
    public static List<String> getModules(String courseCode) {

        List<String> modules = new ArrayList<>();

        String sql = """
                SELECT ModuleCode
                FROM modul
                WHERE CourseCode = ?
                AND Status = 'Active'
                """;

        try (
                Connection conn = EpokConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setString(1, courseCode);

            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    modules.add(rs.getString("ModuleCode"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return modules;
    }
}
