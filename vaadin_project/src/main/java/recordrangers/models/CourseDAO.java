import java.sql.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class CourseDAO {
    private Connection connection;

    public CourseDAO(Connection connection) {
        this.connection = connection;
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

    // ================= UNIT TESTS BELOW =================

    private static Connection testConnection;
    private static CourseDAO courseDAO;

    @BeforeAll
    public static void setupDatabaseConnection() {
        try {
            testConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/your_database", "your_user", "your_password");
            courseDAO = new CourseDAO(testConnection);
        } catch (SQLException e) {
            fail("Database connection failed: " + e.getMessage());
        }
    }

    @AfterAll
    public static void closeDatabaseConnection() {
        try {
            if (testConnection != null) {
                testConnection.close();
            }
        } catch (SQLException e) {
            System.err.println("Error closing database connection: " + e.getMessage());
        }
    }

    @Test
    public void testAddStudentToWaitlist() {
        int studentId = 105;  // Example student ID
        int courseId = 302;   // Example course ID that is full

        boolean isWaitlisted = courseDAO.addStudentToWaitlist(studentId, courseId);
        assertTrue(isWaitlisted, "Student should be added to the waitlist if the course is full");

        // Cleanup: Remove test waitlist entry
        try (PreparedStatement stmt = testConnection.prepareStatement("DELETE FROM waitlists WHERE student_id = ? AND course_id = ?")) {
            stmt.setInt(1, studentId);
            stmt.setInt(2, courseId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Failed to delete test waitlist entry: " + e.getMessage());
        }
    }
}
