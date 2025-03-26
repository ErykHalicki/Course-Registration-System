package recordrangers.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
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
        String query = "SELECT * FROM courses WHERE course_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, courseId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Course(
                    rs.getInt("course_id"),
                    rs.getString("course_code"),
                    rs.getString("course_name"),
                    rs.getInt("max_capacity"),
                    rs.getString("schedule")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("CallToPrintStackTrace")
    public static boolean updateCourseCapacity(int courseId, int newCapacity) {
        String query = "UPDATE courses SET max_capacity = ? WHERE course_id = ?";
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
        String checkCapacityQuery = "SELECT enrollment, maxCapacity FROM courses WHERE course_id = ?";
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
            return rst.getInt("enrolled");
        }
    }

    public static ArrayList<Course> getEnrolledCourses(int studentId) throws SQLException {
        ArrayList<Course> courses = new ArrayList<>();
        String sql = "SELECT course_name, course_code, num_credits, term_label, start_date, end_date " + 
                     "FROM Course JOIN Enrollments " + 
                     "ON Course.course_id = Enrollments.course_id " + 
                     "WHERE Enrollments.student_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
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
            throw new SQLException("Error fetching course enrollments: " + e.getMessage());
        }
        // Add lab enrollments to course array as well
        String labs = "SELECT l.lab_name, c.course_code, c.term_label, c.start_date, c.end_date " + 
                      "FROM Labs as l JOIN Course as c ON l.course_id = c.course_id JOIN LabSection as ls ON l.lab_id = ls.lab_id " +
                      "JOIN LabEnrollment as ON le.section_id = ls.section_id  " + 
                      "WHERE le.student_id = ?";
        try(PreparedStatement pstmt = connection.prepareStatement(labs)) {
            pstmt.setInt(1, studentId);
            ResultSet rst = pstmt.executeQuery();
            while(rst.next()) {
                String name = rst.getString("lab_name");
                String code = rst.getString("course_code");
                int credits = 0;
                String term = rst.getString("term_label");
                LocalDate startDate = LocalDate.parse(rst.getString("start_date"));
                LocalDate endDate = LocalDate.parse(rst.getString("end_date"));
                Course course = new Course(name, code, credits, term, startDate, endDate);
                courses.add(course);
            }
        } catch(SQLException e) {
            throw new SQLException("Error fecthing lab enrollments: " + e.getMessage());
        }
                      
        return courses;
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
