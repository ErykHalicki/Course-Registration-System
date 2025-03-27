package recordrangers.services;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import recordrangers.models.Course;


public class AdminCourseUses {
    private Connection connection;

    public AdminCourseUses() throws SQLException {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    public void addCourse(String name, String code, int credits, String desc, int capacity, String startDate, 
    String endDate, String term, String days, String startTime, String endTime, String location) 
    throws SQLException {
    	connection = DatabaseConnection.getInstance().getConnection();
        String sql = "INSERT INTO Course (course_name, course_code, num_credits, description, capacity, start_date, end_date, term_label, days, start_time, end_time, location) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, code);
            pstmt.setInt(3, credits);
            pstmt.setString(4, desc);
            pstmt.setInt(5, capacity);
            pstmt.setString(6, startDate);
            pstmt.setString(7, endDate);
            pstmt.setString(8, term);
            pstmt.setString(9, days);
            pstmt.setString(10, startTime);
            pstmt.setString(11, endTime);
            pstmt.setString(12, location);

            System.out.println(pstmt.executeUpdate());

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void editCourse(Course course) throws SQLException{
        
        String name = course.getCourseName();
        int credits = course.getNumCredits();
        String desc = course.getDescription();
        int capacity = course.getMaxCapacity();
        String startDate = course.getEndDate().toString();
        String endDate = course.getStartDate().toString();
        String term = course.getTermLabel();
        String days = course.getDays();
        String startTime = course.getStartTime().toString();
        String endTime = course.getEndTime().toString();
        String location = course.getLocation();
        String code = course.getCourseCode();

        String sql = "UPDATE Course SET " +
        "course_name = ?, " +
        "num_credits = ?, " +
        "description = ?, " +
        "capacity = ?, " +
        "start_date = ?, " +
        "end_date = ?, " +
        "term_label = ?, " +
        "days = ?, " +
        "start_time = ?, " +
        "end_time = ?, " +
        "location = ? " +
        "WHERE course_code = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
	        pstmt.setString(1, name);
	        pstmt.setInt(2, credits);
	        pstmt.setString(3, desc);
	        pstmt.setInt(4, capacity);
	        pstmt.setString(5, startDate);
	        pstmt.setString(6, endDate);
	        pstmt.setString(7, term);
	        pstmt.setString(8, days);
	        pstmt.setString(9, startTime);
	        pstmt.setString(10, endTime);
	        pstmt.setString(11, location);
	        pstmt.setString(12, code);
	
	        pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void deleteCourse(String code) throws SQLException{
        String sql = "DELETE FROM Course WHERE course_code = ?";
        

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, code);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
