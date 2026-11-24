package DB;
import databaseConnection.LadokConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class LadokDB {
    public static boolean studentExists(String personnummer) throws SQLException {

        String sql = "SELECT 1 FROM Student WHERE Personnr = ?";

        try (
                Connection conn = LadokConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, personnummer);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }
    public static int findModulID(int moduleCode) throws SQLException {

        String sql = "SELECT ModulID FROM Modul WHERE ModuleCode = ?";

        try (
                Connection conn = LadokConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, moduleCode);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("ModulID");
                }
            }
        }

        return 0;
    }
    public static boolean resultExists(String personnummer, int modulID) throws SQLException {

        String sql = "SELECT 1 FROM Result WHERE Personnr = ? AND ModulID = ?";

        try (
                Connection conn = LadokConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, personnummer);
            stmt.setInt(2, modulID);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }
    public static void insertResult(String personnummer, int modulID,
                                    String examDate, String grade) throws SQLException {


           String sql = """
            INSERT INTO Result
            (Personnr, ModulID, ExamDate, FinalGrade, Registered)
            VALUES (?, ?, ?, ?, TRUE)
            """;

        try (
                Connection conn = LadokConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, personnummer);
            stmt.setInt(2, modulID);
            stmt.setDate(3, java.sql.Date.valueOf(examDate));
            stmt.setString(4, grade);

            stmt.executeUpdate();
        }

    }

    public static List<String> getResultsByModule(int moduleCode)
            throws SQLException {

        List<String> results = new ArrayList<>();

        String sql = """
            SELECT r.Personnr, r.FinalGrade, r.ExamDate
            FROM Result r
            JOIN Modul m ON r.ModulID = m.ModulID
            WHERE m.ModuleCode = ?
            """;

        try (
                Connection conn = LadokConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, moduleCode);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {

                    String result =
                            rs.getString("Personnr") + " | " +
                                    rs.getString("FinalGrade") + " | " +
                                    rs.getString("ExamDate");

                    results.add(result);
                }
            }
        }

        return results;
    }

}
