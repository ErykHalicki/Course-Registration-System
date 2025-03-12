import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import recordrangers.models.Course;
import recordrangers.services.CourseDAO;
import recordrangers.services.DatabaseConnection;

public class CourseRegistration {
    
    private Connection con;

    public CourseRegistration() throws SQLException {
        this.con = DatabaseConnection.getConnection();
    }

    public void registerStudent(int studentId, int courseId) throws SQLException {
        // check that student is not already registered
        // check that course capacity is not full
        Course course = CourseDAO.getCourseDetails(courseId);
        int capacity = course.getMaxCapacity();
        int enrolled = CourseDAO.courseCapacity(courseId);

        // Check that the course is not full, if so return
        if (enrolled >= capacity) {
            throw new SQLException("Course is full. Cannot register student.");
        }

        // Check that student is not already enrolled in this course
        if (isRegistered(courseId, studentId)) {
            throw new SQLException("Student is already enrolled in this course.");
        }
        
        String sql = "INSERT INTO Enrollments (student_id, course_id, status) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, studentId);
            pstmt.setInt(2, courseId);
            pstmt.setString(3, "Enrolled");
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error registering a student: " + e.getMessage());
        }
    }

    // Helper method to return student enrollment in a course
    public boolean isRegistered(int courseId, int studentId) throws SQLException {
        // Return 1 if student is registered
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

    public void dropCourse(int studentId, int courseId) throws SQLException {
        // Start a transaction
        con.setAutoCommit(false);

        // Try the transaction and catch errors
        try {
            // First remove student from course
            removeStudentFromCourse(studentId, courseId);

            // Next drop their lab sections of this course
            removeStudentFromLabSections(studentId, courseId);

            // Lastly enroll next student from waitlist
            enrollNextStudentFromWaitlist(studentId, null, "Course");

            // Commit the transaction 
            con.commit();

        } catch (SQLException e) {
            con.rollback(); // Undo's any changes made
            throw new SQLException("Failed to drop the couse: " + e.getMessage());
        } finally {
            // Restore con auto commit mode
            con.setAutoCommit(true);
        }
    }
    
    private void removeStudentFromCourse(int studentId, int courseId) throws SQLException {
        String sql = "DELETE FROM Enrollments WHERE student_id = AND course_id = ?";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, studentId);
            pstmt.setInt(2, courseId);
            pstmt.executeUpdate();
        }
    }

    private void removeStudentFromLabSections(int studentId, int courseId) throws SQLException {
        String sql = "SELECT section_id FROM LabSection WHERE lab_id IN " +
                     "(SELECT lab_id FROM Labs WHERE course_id = ?)";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, courseId);
            ResultSet rst = pstmt.executeQuery();
            while (rst.next()) {
                int sectionId = rst.getInt("section_id");
                // Remove student from the lab section
                removeStudentFromLabSection(studentId, sectionId);

                // Enroll next student from waitlist into lab section
                enrollNextStudentFromWaitlist(studentId, sectionId, "Lab");
            }
        }
    }

    private void removeStudentFromLabSection(int studentId, int sectionId) throws SQLException {
        String sql = "DELETE FROM LabEnrollment WHERE student_id = ? AND section_id = ?";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, studentId);
            pstmt.setInt(2, sectionId);
            pstmt.executeUpdate();
        }
    }

    public void enrollNextStudentFromWaitlist(int studentId, Integer sectionId, String type) throws SQLException {

    }
 }
