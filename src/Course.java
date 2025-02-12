import java.sql.*;

class Course {
    public int courseId;
    public String courseCode;
    public String courseName;
    public int maxCapacity;
    public String schedule;
       
	public Course(){}
	public Course(String courseCode) {}
    public Course(int courseId, String courseCode, String courseName, int maxCapacity, String schedule) {
        this.courseId = courseId;
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.maxCapacity = maxCapacity;
        this.schedule = schedule;
    }

    public static Course getCourseDetails(int courseId) {
        String query = "SELECT * FROM courses WHERE course_id = ?";
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/your_database", "your_username", "your_password");
             PreparedStatement stmt = conn.prepareStatement(query)) {
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

    public static boolean updateCourseCapacity(int courseId, int newCapacity) {
        String query = "UPDATE courses SET max_capacity = ? WHERE course_id = ?";
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/your_database", "your_username", "your_password");
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, newCapacity);
            stmt.setInt(2, courseId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
