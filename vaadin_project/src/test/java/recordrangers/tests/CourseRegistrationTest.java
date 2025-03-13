import java.sql.Connection;
import java.sql.*;
import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import recordrangers.services.DatabaseConnection;

public class CourseRegistrationTest {

    private static CourseRegistration c;
    private static Connection con;

    @BeforeClass
    public void init() throws SQLException {
        this.c = new CourseRegistration();
        this.con = DatabaseConnection.getConnection();
    }

    @Test
    public void testRegisterStudent() throws SQLException {
        c.registerStudent(1, 1);
        Assert.assertTrue(studentIsEnrolled(1, 1));
    }

   public static boolean studentIsEnrolled(int studentId, int courseId) throws SQLException {
    String sql = "SELECT 1 FROM Enrollments WHERE course_id= ? AND student_id = ? AND status = 'Enrolled'";

    try (PreparedStatement pstmt = con.prepareStatement(sql)) {
        pstmt.setInt(1, courseId);
        pstmt.setInt(2, studentId);
        ResultSet rst = pstmt.executeQuery();
        return rst.next(); // If a row exists then the registration worked
    }
    }
}
