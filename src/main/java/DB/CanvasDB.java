package DB;

import databaseConnection.CanvasConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CanvasDB {

    // Returns all available course codes
    public static List<String> getCourses() {

        List<String> courses = new ArrayList<>();

        String sql = "SELECT CourseCode FROM Course";

        try (
                Connection conn = CanvasConnection.getConnection();
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

    // Returns assignments for selected course
    public static List<String> getAssignments(String courseCode) {

        List<String> assignments = new ArrayList<>();

        String sql = """
                SELECT a.ExamType
                FROM Assignment a
                JOIN Course c ON a.CourseID = c.CourseID
                WHERE c.CourseCode = ?
                """;

        try (
                Connection conn = CanvasConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setString(1, courseCode);

            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    assignments.add(rs.getString("ExamType"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return assignments;
    }

    // Returns grades for selected assignment
    public static List<String> getGrades(String examType) {

        List<String> grades = new ArrayList<>();

        String sql = """
                SELECT g.Grade
                FROM Grade g
                JOIN Assignment a ON g.ExamID = a.ExamID
                WHERE a.ExamType = ?
                """;

        try (
                Connection conn = CanvasConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setString(1, examType);

            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    grades.add(rs.getString("Grade"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return grades;
    }

    // Returns students for selected assignment
    public static List<String> getStudents(String examType) {

        List<String> students = new ArrayList<>();

        String sql = """
                SELECT s.StudentID
                FROM Student s
                JOIN Course_Student cs
                    ON s.StudentID = cs.StudentID
                JOIN Grade g
                    ON cs.course_student_id = g.course_student_id
                JOIN Assignment a
                    ON g.ExamID = a.ExamID
                WHERE a.ExamType = ?
                """;

        try (
                Connection conn = CanvasConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setString(1, examType);

            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    students.add(rs.getString("StudentID"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return students;
    }
}