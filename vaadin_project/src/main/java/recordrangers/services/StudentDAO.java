package recordrangers.services;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentDAO{
    private static Connection connection;
        
        public StudentDAO() throws SQLException{
            StudentDAO.connection = DatabaseConnection.getConnection();
        }
        public StudentDAO(Connection connection){
            StudentDAO.connection = connection;
        }
    
    @SuppressWarnings("CallToPrintStackTrace")
        public static String enrollStudent(int studentId, int courseId) {
            String queryCheckEnrollment = "SELECT * FROM enrollments WHERE student_id = ? AND course_id = ?";
            String queryCourseCapacity = "SELECT COUNT(*) AS enrolled, max_capacity FROM enrollments " +
                                         "JOIN courses ON enrollments.course_id = courses.course_id WHERE courses.course_id = ?";
            String queryEnroll = "INSERT INTO enrollments (student_id, course_id) VALUES (?, ?)";
            String queryWaitlist = "INSERT INTO waitlists (student_id, course_id) VALUES (?, ?)";
            
            try (PreparedStatement checkStmt = connection.prepareStatement(queryCheckEnrollment);
             PreparedStatement capacityStmt = connection.prepareStatement(queryCourseCapacity);
             PreparedStatement enrollStmt = connection.prepareStatement(queryEnroll);
             PreparedStatement waitlistStmt = connection.prepareStatement(queryWaitlist)) {
            
            // Check if student is already enrolled
            checkStmt.setInt(1, studentId);
            checkStmt.setInt(2, courseId);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) {
                return "Student is already enrolled in this course.";
            }
            
            // Check course capacity
            capacityStmt.setInt(1, courseId);
            rs = capacityStmt.executeQuery();
            if (rs.next()) {
                int enrolled = rs.getInt("enrolled");
                int maxCapacity = rs.getInt("max_capacity");
                
                if (enrolled < maxCapacity) {
                    // Enroll the student
                    enrollStmt.setInt(1, studentId);
                    enrollStmt.setInt(2, courseId);
                    enrollStmt.executeUpdate();
                    return "Student successfully enrolled.";
                } else {
                    // Add student to waitlist
                    waitlistStmt.setInt(1, studentId);
                    waitlistStmt.setInt(2, courseId);
                    waitlistStmt.executeUpdate();
                    return "Course is full. Student added to waitlist.";
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Database error: " + e.getMessage();
        }
        return "Enrollment process failed.";
    }
}
