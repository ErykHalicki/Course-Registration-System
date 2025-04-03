package recordrangers.services;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import recordrangers.models.Course;

public class CourseRegistration {
    
    private static Connection con;
    private static LabRegistration labService;

    public CourseRegistration() throws SQLException {
        con = DatabaseConnection.getInstance().getConnection();
        labService = new LabRegistration();
    }

    public static String registerStudent(int studentId, int courseId) throws SQLException {
        con = DatabaseConnection.getInstance().getConnection();
        // check that student is not already registered
        // check that course capacity is not full
        Course course = CourseDAO.getCourseDetails(courseId);
        int capacity = course.getMaxCapacity();
        int enrolled = CourseDAO.courseCapacity(courseId);

        // Check that the course is not full, if so return
        if (enrolled >= capacity) {
        	return "Course is full. Cannot register student.";
        }

        // Check that student is not already enrolled in this course
        if (isRegistered(courseId, studentId)) {
        	return "Student is already enrolled in this course.";
        }
        
        String sql = "INSERT INTO Enrollments (student_id, course_id, status) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, studentId);
            pstmt.setInt(2, courseId);
            pstmt.setString(3, "Enrolled");
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error registering a student: " + e.getMessage());
            throw e;
        }
        return "successfully registered student";
    }

    // Helper method to return student enrollment in a course
    public static boolean isRegistered(int courseId, int studentId) throws SQLException {
        // Return 1 if student is registered
        con = DatabaseConnection.getInstance().getConnection();
        String sql = "SELECT 1 FROM Enrollments WHERE course_id = ? AND student_id = ? " + " AND status = 'Enrolled'";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            // Set courseId and studentId
            pstmt.setInt(1, courseId);
            pstmt.setInt(2, studentId);
            ResultSet rst = pstmt.executeQuery();
            if (rst.next()) {
                // Returns true if a row exists
                return true;
            }
        }
        return false;
    }

    public static void dropCourse(int studentId, int courseId) throws SQLException {
        // Start a transaction
        con = DatabaseConnection.getInstance().getConnection();
        con.setAutoCommit(false);

        // Try the transaction and catch errors
        try {
            // First remove student from course
            removeStudentFromCourse(studentId, courseId);
            
            // Next drop their lab sections of this course
            LabRegistration.removeStudentFromLabSections(studentId, courseId);

            // Lastly enroll next student from waitlist
            //WaitlistHandler.enrollNextStudentFromWaitlist(studentId, 0, "Course");
            
            // Commit the transaction 
            con.commit();

        } catch (SQLException e) {
            con.rollback(); // Undo's any changes made
            e.printStackTrace();
            throw e;
        } finally {
            // Restore con auto commit mode
            con.setAutoCommit(true);
        }
    }
    
    private static void removeStudentFromCourse(int studentId, int courseId) throws SQLException {
        con = DatabaseConnection.getInstance().getConnection();
        System.out.println("Trying to drop course: "+ courseId);
        String sql = "DELETE FROM Enrollments WHERE student_id = ? AND course_id = ?";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, studentId);
            pstmt.setInt(2, courseId);
            pstmt.executeUpdate();
        }
    }
}