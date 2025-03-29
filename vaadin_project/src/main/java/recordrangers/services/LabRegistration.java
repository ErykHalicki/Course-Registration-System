package recordrangers.services;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LabRegistration {
    
    private static Connection con;

    public LabRegistration() throws SQLException {
        con = DatabaseConnection.getInstance().getConnection();
    }

    public static void registerStudentIntoLab(int studentId, int sectionId) throws SQLException {
        con = DatabaseConnection.getInstance().getConnection();
        String sql = "INSERT INTO LabEnrollment (student_id, section_id) VALUES (?, ?)";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, studentId);
            pstmt.setInt(2, sectionId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error registering a student into the lab! " + e.getMessage());
            throw e;
        }
    }

    public static void removeStudentFromLabSection(int studentId, int sectionId) throws SQLException {
        con = DatabaseConnection.getInstance().getConnection();
        String sql = "DELETE FROM LabEnrollment WHERE student_id = ? AND section_id = ?";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, studentId);
            pstmt.setInt(2, sectionId);
            pstmt.executeUpdate();
        }
    }

    public static void removeStudentFromLabSections(int studentId, int courseId) throws SQLException {
        con = DatabaseConnection.getInstance().getConnection();
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
                // This would call the WaitlistService if needed
                WaitlistHandler.enrollNextStudentFromWaitlist(studentId, sectionId, "Lab");
            }
        }
    }
}