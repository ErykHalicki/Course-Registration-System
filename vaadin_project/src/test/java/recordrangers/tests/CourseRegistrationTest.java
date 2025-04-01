package recordrangers.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import recordrangers.services.CourseDAO;
import recordrangers.services.CourseRegistration;
import recordrangers.services.LabRegistration;
import recordrangers.services.DatabaseConnection;

public class CourseRegistrationTest {
    private Connection con;

    @Test
    void testRegisterStudent() throws SQLException {
        int studentId = 1; // Replace with a valid test student ID
        int courseId = 1; // Replace with a valid test course ID
        CourseRegistration.dropCourse(studentId, courseId);
        // Ensure student is not already registered
        assertFalse(CourseRegistration.isRegistered(courseId, studentId));
        
        // Register student
        CourseRegistration.registerStudent(studentId, courseId);
        
        // Verify student is now registered
        assertTrue(CourseRegistration.isRegistered(courseId, studentId));
    }

    @Test
    void testDropCourse() throws SQLException {
        int studentId = 4;
        int courseId = 2;

        // Register student
        CourseRegistration.registerStudent(studentId, courseId);
        assertTrue(CourseRegistration.isRegistered(courseId, studentId));

        // Drop course
        CourseRegistration.dropCourse(studentId, courseId);
        assertFalse(CourseRegistration.isRegistered(courseId, studentId));
    }

    @Test
    void testRegisterStudentIntoLab() throws SQLException {
        int studentId = 1;
        int sectionId = 1; // Valid lab section
        
        LabRegistration.registerStudentIntoLab(studentId, sectionId);

        // Verify student is enrolled in lab
        String sql = "SELECT * FROM LabEnrollment WHERE student_id = ? AND section_id = ?";
        try (PreparedStatement pstmt = DatabaseConnection.getInstance().getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, studentId);
            pstmt.setInt(2, sectionId);
            ResultSet rst = pstmt.executeQuery();
            assertTrue(rst.next());
        }
    }

    @Test
    void testRemoveStudentFromLabSection() throws SQLException {
        int studentId = 6;
        int sectionId = 2;

        // Enroll in lab first
        LabRegistration.registerStudentIntoLab(studentId, sectionId);

        // Remove from lab
        LabRegistration.removeStudentFromLabSection(studentId, sectionId);

        // Verify student is no longer enrolled
        String sql = "SELECT 1 FROM LabEnrollment WHERE student_id = ? AND section_id = ?";
        try (PreparedStatement pstmt = DatabaseConnection.getInstance().getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, studentId);
            pstmt.setInt(2, sectionId);
            ResultSet rst = pstmt.executeQuery();
            assertFalse(rst.next());
        }
    }
}
