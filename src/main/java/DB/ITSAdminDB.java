package DB;

import databaseConnection.CanvasConnection;
import databaseConnection.ITSAdminConnection;
import model.StudentTeacher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ITSAdminDB {

    public static List<StudentTeacher> getAllUsers() {
        List<StudentTeacher> users = new ArrayList<>();
        String sql = """
                SELECT personnr, studentid, fname, lname, email, status
                FROM StudentTeacherInfo
                """;

        try (
                Connection conn = ITSAdminConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()
        ) {
            while (rs.next()) {
                users.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch users from StudentTeacherInfo", e);
        }

        return users;
    }

    public static StudentTeacher getUserByStudentId(String studentId) {
        String sql = """
                SELECT personnr, studentid, fname, lname, email, status
                FROM StudentTeacherInfo
                WHERE studentid = ?
                """;

        try (
                Connection conn = ITSAdminConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, studentId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch user by studentId: " + studentId, e);
        }

        return null;
    }

    public static boolean createUser(
            String personnr,
            String studentId,
            String fName,
            String lName,
            String email,
            String password,
            String status
    ) {
        String sql = """
                INSERT INTO StudentTeacherInfo
                (personnr, studentid, fname, lname, email, password, status)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        try (
                Connection conn = ITSAdminConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, personnr);
            stmt.setString(2, studentId);
            stmt.setString(3, fName);
            stmt.setString(4, lName);
            stmt.setString(5, email);
            stmt.setString(6, password);
            stmt.setString(7, status);

            return stmt.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to insert user into StudentTeacherInfo", e);
        }
    }

    private static StudentTeacher mapRow(ResultSet rs) throws SQLException {
        return new StudentTeacher(
                rs.getString("personnr"),
                rs.getString("studentid"),
                rs.getString("fname"),
                rs.getString("lname"),
                rs.getString("email"),
                rs.getString("status")
        );
    }
}

