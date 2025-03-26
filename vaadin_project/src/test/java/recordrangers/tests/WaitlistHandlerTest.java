package recordrangers.tests;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.*;

import recordrangers.services.DatabaseConnection;
import recordrangers.services.WaitlistHandler;

public class WaitlistHandlerTest {
    private WaitlistHandler waitlistHandler;


    @Test
    public void addToWaitlist() throws SQLException { 
        // Course waitlist
        int studentId = 1;
        int courseId = 1;
        String type = "Course";
        WaitlistHandler.addToWaitlist(studentId, courseId, 0, type);
        String sql = "SELECT 1 FROM Waitlist WHERE student_id = ? AND course_id = ? AND type = ?";
        try (PreparedStatement pstmt = DatabaseConnection.getInstance().getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, studentId);
            pstmt.setInt(2, courseId);
            pstmt.setString(3, type);
            ResultSet rst = pstmt.executeQuery();
            assertTrue(rst.next());
        }
    }

    @Test
    public void addToLabWaitlist() throws SQLException { 
        // Lab waitlist
        int studentId = 1;
        int courseId = 1;
        int sectionId = 1;
        String type = "Lab";
        WaitlistHandler.addToWaitlist(studentId, courseId, sectionId, type);
        String sql = "SELECT 1 FROM Waitlist WHERE student_id = ? AND course_id = ? AND section_id = ? AND type = ?";
        try (PreparedStatement pstmt = DatabaseConnection.getInstance().getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, studentId);
            pstmt.setInt(2, courseId);
            pstmt.setInt(3, sectionId);
            pstmt.setString(4, type);
            ResultSet rst = pstmt.executeQuery();
            assertTrue(rst.next());
        }
    }
}
