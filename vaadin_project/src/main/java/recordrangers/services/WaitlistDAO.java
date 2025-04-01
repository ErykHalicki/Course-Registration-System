package recordrangers.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import recordrangers.models.Course;

public class WaitlistDAO {
    private static Connection connection;

    public WaitlistDAO() throws SQLException {
        WaitlistDAO.connection = DatabaseConnection.getInstance().getConnection();
    }

    // Method to add a student to the waitlist if the course is full
    public static boolean addStudentToWaitlist(int studentId, int courseId) {
        // Check if the course is full
        String checkCapacityQuery = "SELECT enrollment, capacity FROM Course WHERE course_id = ?";
        try (PreparedStatement checkStmt = DatabaseConnection.getInstance().getConnection().prepareStatement(checkCapacityQuery)) {
            checkStmt.setInt(1, courseId);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                int enrollment = rs.getInt("enrollment");
                int maxCapacity = rs.getInt("capacity");

                if (enrollment >= maxCapacity) {
                    // Course is full, insert student into waitlist
                    String insertWaitlistQuery = "INSERT INTO waitlists (student_id, course_id, request_date) VALUES (?, ?, CURRENT_TIMESTAMP)";
                    try (PreparedStatement insertStmt = DatabaseConnection.getInstance().getConnection().prepareStatement(insertWaitlistQuery)) {
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
    
    public static ArrayList<Course> getWaitlistedCourses(int studentId) throws SQLException {
        ArrayList<Course> courses = new ArrayList<>();
        String waitlist = "SELECT c.course_name, c.course_code, c.num_credits, c.term_label, c.start_date, c.end_date " + 
                         "FROM Course as c JOIN Waitlist as w ON c.course_id = w.course_id " + 
                         "WHERE w.student_id = ?";
        try(PreparedStatement pstmt = DatabaseConnection.getInstance().getConnection().prepareStatement(waitlist)) {
            pstmt.setInt(1, studentId);
            ResultSet rst = pstmt.executeQuery();
            while(rst.next()) {
                String name = rst.getString("course_name");
                String code = rst.getString("course_code");
                int credits = rst.getInt("num_credits");
                String term = rst.getString("term_label");
                LocalDate startDate = LocalDate.parse(rst.getString("start_date"));
                LocalDate endDate = LocalDate.parse(rst.getString("end_date"));
                Course course = new Course(name, code, credits, term, startDate, endDate);
                courses.add(course);
            }
        } catch(SQLException e) {
            throw new SQLException("Error fetching waitlisted courses: " + e.getMessage());
        }
        return courses;
    }
}