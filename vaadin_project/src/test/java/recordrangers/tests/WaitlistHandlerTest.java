import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.BeforeClass;
import org.junit.Test;

import recordrangers.services.DatabaseConnection;

public class WaitlistHandlerTest {
    private WaitlistHandler waitlistHandler;
    private DatabaseConnection dbConnection = DatabaseConnection.getInstance();
    private Connection con;

    @BeforeClass
    public void init() throws SQLException {
        this.waitlistHandler = new WaitlistHandler();
        this.con = dbConnection.getConnection();
    }

    @Test
    public void addToWaitlist() throws SQLException { 
        // Course waitlist
        int studentId = 1;
        int courseId = 1;
        String type = "Course";
        waitlistHandler.addToWaitlist(studentId, courseId, 0, type);
        String sql = "SELECT 1 FROM Waitlist student_id = ? AND course_id = ? AND type = ?";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, studentId);
            pstmt.setInt(2, courseId);
            pstmt.setString(3, type);
            ResultSet rst = pstmt.executeQuery();
            Assert.assertTrue(rst.next());
        }
    }

    public void addToLabWaitlist() throws SQLException { 
        // Lab waitlist
        int studentId = 1;
        int courseId = 1;
        int sectionId = 1;
        String type = "Course";
        waitlistHandler.addToWaitlist(studentId, courseId, sectionId, type);
        String sql = "SELECT 1 FROM Waitlist student_id = ? AND course_id = ? AND section_id = ? AND type = ?";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, studentId);
            pstmt.setInt(2, courseId);
            pstmt.setInt(3, sectionId);
            pstmt.setString(4, type);
            ResultSet rst = pstmt.executeQuery();
            Assert.assertTrue(rst.next());
        }
    }
}
