package recordrangers.services;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import recordrangers.services.CourseRegistration;
import recordrangers.services.DatabaseConnection;

public class WaitlistHandler {
    
    private DatabaseConnection dbConnection = DatabaseConnection.getInstance();
    private Connection con;

    public WaitlistHandler() throws SQLException {
        this.con = dbConnection.getConnection();
    }

    public static void addToWaitlist(int studentId, int courseId, int sectionId, String type) throws SQLException {
        // Check that type is valid
        if (!type.equals("Lab") && !type.equals("Course")) {
            throw new IllegalArgumentException("Type must be of Course or Lab");
        }

        int position = getNextWaitlistPosition(courseId, sectionId, type);

        String sql;
        if (type.equals("Course")) {
            sql = "INSERT INTO Waitlist (student_id, course_id, type, position) VALUES (?, ?, ?, ?)";
        } else {
            sql = "INSERT INTO Waitlist (student_id, course_id, section_id, type, position) VALUES (?, ?, ?, ?, ?)";
        }
        
        try (PreparedStatement pstmt = DatabaseConnection.getInstance().getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, studentId);
            pstmt.setInt(2, courseId);
            if (type.equals("Course")) {
                pstmt.setString(3, type);
                pstmt.setInt(4, position);
            } else {
                pstmt.setInt(3, sectionId);
                pstmt.setString(4, type);
                pstmt.setInt(5, position);
            }
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Failed to add student to waitlist ", e.getMessage());
        }
    }

    public static int getNextWaitlistPosition(int courseId, int sectionId, String type) throws SQLException {
        String sql; // Gets max position + 1 or 0 if null using coalesce
        if (type.equals("Course")) {
            sql = "SELECT COALESCE(MAX(position), 0) + 1 as nextPosition FROM Waitlist " + 
            "WHERE course_id = ? AND type = ?";
        } else {
            sql = "SELECT COALESCE(MAX(position), 0) + 1 as nextPosition FROM Waitlist " + 
            "WHERE course_id = ? AND section_id = ? AND type = ?";
        }

        try (PreparedStatement pstmt = DatabaseConnection.getInstance().getConnection().prepareStatement(sql)) {
            if (type.equals("Course")) {
                pstmt.setInt(1, courseId);
                pstmt.setString(2, type);
            } else {
                pstmt.setInt(1, courseId);
                pstmt.setInt(2, sectionId);
                pstmt.setString(3, type);
            }

            try (ResultSet rst = pstmt.executeQuery()) {
                if (rst.next()) {
                    return rst.getInt("nextPosition");
                } else {
                    throw new SQLException("Unable to retrieve the next waitlist position");
                }
            }
        }
    }

    public static void enrollNextStudentFromWaitlist(int courseId, int sectionId, String type) throws SQLException {
        String sql;
        if (type.equals("Course")) {
            sql = "SELECT waitlist_id, student_id FROM Waitlist WHERE course_id = ? AND type = ? " + 
            "ORDER BY position ASC LIMIT 1";
        } else {
            sql = "SELECT waitlist_id, student_id FROM Waitlist WHERE course_id = ? AND section_id = ? AND type = ? " +
            "ORDER BY position ASC LIMIT 1";
        }

        try (PreparedStatement pstmt = DatabaseConnection.getInstance().getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, courseId);
            if (type.equals("Course")) {
                pstmt.setString(2, type);
            } else {
                pstmt.setInt(2, sectionId);
                pstmt.setString(3, type);
            }

            try (ResultSet rst = pstmt.executeQuery()) {
                if (rst.next()) {
                    CourseRegistration cr = new CourseRegistration();
                    int waitlistId = rst.getInt("waitlist_id");
                    int studentId = rst.getInt("student_id");
                    if (type.equals("Course")) {
                        cr.registerStudent(studentId, courseId);
                    } else if (type.equals("Lab")) {
                        cr.registerStudent(studentId, sectionId);
                    }

                    removeFromWaitlist(waitlistId);
                }
            } 
        } catch (SQLException e) {
            throw new SQLException("Failed to enroll next student from waitlist ", e.getMessage());
        }
    }

    public static void removeFromWaitlist(int waitlistId) throws SQLException {
        String sql = "REMOVE FROM Waitlist WHERE waitlist_id = ?";
        try (PreparedStatement pstmt = DatabaseConnection.getInstance().getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, waitlistId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Failed to remove student from waitlist ", e.getMessage());
        }
    }
}
