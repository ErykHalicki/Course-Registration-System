import java.sql.*;
import java.util.*;

public class CourseDAO {
    private static Connection connection;

    public CourseDAO() throws SQLException{
        CourseDAO.connection = DatabaseConnection.getConnection();
    }
    public CourseDAO(Connection connection){
        CourseDAO.connection = connection;
    }
    public ArrayList<Course> searchByCourseName(String name) throws SQLException{
        ArrayList<Course> courses = new ArrayList<>();
        String sql = 
        "SELECT code, name " + 
        "FROM Courses " +
        "WHERE code = ? OR name = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
        pstmt.setString(1, name);
        pstmt.setString(1, name);
            try (ResultSet rst = pstmt.executeQuery()){
                while(rst.next()){
                    String currentCode = rst.getString("code");
                    Course currentCourse = new Course(currentCode);
                    courses.add(currentCourse);
                }
            }
        }

        return courses;
    }

    public static Course getCourseDetails(int courseId) {
        String query = "SELECT * FROM courses WHERE course_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, courseId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Course(
                    rs.getInt("course_id"),
                    rs.getString("course_code"),
                    rs.getString("course_name"),
                    rs.getInt("max_capacity"),
                    rs.getString("schedule")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean updateCourseCapacity(int courseId, int newCapacity) {
        String query = "UPDATE courses SET max_capacity = ? WHERE course_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, newCapacity);
            stmt.setInt(2, courseId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Method to add a student to the waitlist if the course is full
    public boolean addStudentToWaitlist(int studentId, int courseId) {
        // Check if the course is full
        String checkCapacityQuery = "SELECT enrollment, maxCapacity FROM courses WHERE course_id = ?";
        try (PreparedStatement checkStmt = connection.prepareStatement(checkCapacityQuery)) {
            checkStmt.setInt(1, courseId);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                int enrollment = rs.getInt("enrollment");
                int maxCapacity = rs.getInt("maxCapacity");

                if (enrollment >= maxCapacity) {
                    // Course is full, insert student into waitlist
                    String insertWaitlistQuery = "INSERT INTO waitlists (student_id, course_id, request_date) VALUES (?, ?, CURRENT_TIMESTAMP)";
                    try (PreparedStatement insertStmt = connection.prepareStatement(insertWaitlistQuery)) {
                        insertStmt.setInt(1, studentId);
                        insertStmt.setInt(2, courseId);
                        int affectedRows = insertStmt.executeUpdate();

                        return affectedRows > 0;
                    }
                } else {
                    System.out.println("Course has available seats. No need to waitlist.");
                    return false;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error adding student to waitlist: " + e.getMessage());
        }
        return false;
    }
}
