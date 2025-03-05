package recordrangers.services;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class AdminCourseUses {
    private final Connection connection;

    public AdminCourseUses() throws SQLException {
        this.connection = DatabaseConnection.getConnection();
    }

    public void addCourse(String name, String code, int credits, String desc, int capacity, String startDate, 
    String endDate, String term, String days, String startTime, String endTime, String location) 
    throws SQLException {
        String sql = "INSERT INTO Courses (name, code, credits, description, capacity, startDate, endDate, term, startTime, endTime, location) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";


        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, code);
            pstmt.setInt(3, credits);
            pstmt.setString(4, desc);
            pstmt.setInt(5, capacity);
            pstmt.setString(6, startDate);
            pstmt.setString(7, endDate);
            pstmt.setString(8, term);
            pstmt.setString(9, days);
            pstmt.setString(10, startTime);
            pstmt.setString(11, endTime);
            pstmt.setString(12, location);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void editCourse(String name, String code, int credits, String desc, int capacity, String startDate, 
    String endDate, String term, String days, String startTime, String endTime, String location) throws SQLException{
        
        String sql = "UPDATE courses SET " +
        "name = ?, " +
        "credits = ?, " +
        "description = ?, " +
        "capacity = ?, " +
        "startDate = ?, " +
        "endDate = ?, " +
        "term = ?, " +
        "days = ?, " +
        "startTime = ?, " +
        "endTime = ?, " +
        "location = ? " +
        "WHERE code = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
        pstmt.setString(1, name);
        pstmt.setInt(2, credits);
        pstmt.setString(3, desc);
        pstmt.setInt(4, capacity);
        pstmt.setString(5, startDate);
        pstmt.setString(6, endDate);
        pstmt.setString(7, term);
        pstmt.setString(8, days);
        pstmt.setString(9, startTime);
        pstmt.setString(10, endTime);
        pstmt.setString(11, location);

        pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void deleteCourse(String code) throws SQLException{
        String sql = "DELETE FROM Courses WHERE code = ?";
        

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, code);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
