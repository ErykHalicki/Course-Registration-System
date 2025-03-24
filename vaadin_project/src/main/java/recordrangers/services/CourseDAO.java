package recordrangers.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import recordrangers.models.Course;

public class CourseDAO {
    private static Connection connection;

    public CourseDAO() throws SQLException{
        CourseDAO.connection =  DatabaseConnection.getInstance().getConnection();
    }
    public ArrayList<Course> searchByCourseCode(String code) throws SQLException{
        ArrayList<Course> courses = new ArrayList<>();
        String sql = 
        "SELECT * FROM Course WHERE course_code = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
        pstmt.setString(1, code);
            try (ResultSet rst = pstmt.executeQuery()){
                while(rst.next()){
                    int currentId = rst.getInt("course_id");
                    String currentCode = rst.getString("course_code");
                    String currentName = rst.getString("course_name");
                    int currentMax = rst.getInt("capacity");
                    String schedule = rst.getString("term_label")+" : "+rst.getString("start_date")+" - "+rst.getString("end_date");
                    Course currentCourse = new Course(currentId, currentCode, currentName, currentMax, schedule);
                    courses.add(currentCourse);
                }
            }
        }
        return courses;
    }

    public static ArrayList<Course> getAllCourses() throws SQLException{
        ArrayList<Course> courses = new ArrayList<>();
        String sql = 
        "SELECT * FROM Course";
        Statement stmt = connection.createStatement(); ;
        try (ResultSet rst = stmt.executeQuery(sql)){
                while(rst.next()){
                    int currentId = rst.getInt("course_id");
                    String currentCode = rst.getString("course_code");
                    String currentName = rst.getString("course_name");
                    int currentMax = rst.getInt("capacity");
                    String schedule = rst.getString("term_label")+" : "+rst.getString("start_date")+" - "+rst.getString("end_date");
                    Course currentCourse = new Course(currentId, currentCode, currentName, currentMax, schedule);
                    courses.add(currentCourse);
            }
        }
        return courses;
    }

    
    @SuppressWarnings("CallToPrintStackTrace")
    public static Course getCourseDetails(int courseId) {
        String query = "SELECT * FROM Course WHERE course_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, courseId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Course(
                    rs.getInt("course_id"),
                    rs.getString("course_code"),
                    rs.getString("course_name"),
                    rs.getInt("capacity"),
                    rs.getString("term_label")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("CallToPrintStackTrace")
    public static boolean updateCourseCapacity(int courseId, int newCapacity) {
        String query = "UPDATE Course SET capacity = ? WHERE course_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, newCapacity);
            stmt.setInt(2, courseId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Method to add a student to the waitlist if the course is full
    public boolean addStudentToWaitlist(int studentId, int courseId) {
        // Check if the course is full
        String checkCapacityQuery = "SELECT enrollment, capacity FROM Course WHERE course_id = ?";
        try (PreparedStatement checkStmt = connection.prepareStatement(checkCapacityQuery)) {
            checkStmt.setInt(1, courseId);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                int enrollment = rs.getInt("enrollment");
                int maxCapacity = rs.getInt("maxCapacity");

                if (enrollment >= maxCapacity) {
                    // Course is full, insert student into waitlist
                    String insertWaitlistQuery = "INSERT INTO waitlists (student_id, course_id, request_date) VALUES (?, ?, CURRENT_TIMESTAMP)";
                    try (PreparedStatement insertStmt = connection.prepareStatement(insertWaitlistQuery)) {
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

    public static int courseCapacity(int courseId) throws SQLException {
        // Select the count of studentIds from the enrolled relation with matching courseId
        // Prepared statement not needed as user does not enter courseId
        String sql = "SELECT COUNT(student_id) as enrolled FROM Enrollments WHERE course_id = " + courseId; 
        try (Statement stmt = connection.createStatement()) {
            ResultSet rst = stmt.executeQuery(sql);
            if (rst.next()) {
            	return rst.getInt("enrolled");
            }
            return 0;
        }
    }

    @SuppressWarnings("CallToPrintStackTrace")
    public static void main(String[] args){
        try {
            CourseDAO courseDAO = new CourseDAO();
            ArrayList<Course> courses = courseDAO.getAllCourses();
            System.out.println(courses);
        } catch (SQLException e) {
            System.out.println(e);
    }
}
}
