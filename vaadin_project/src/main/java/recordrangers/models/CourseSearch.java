import java.sql.*;
import java.util.ArrayList;
public class CourseSearch {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/your_database";
    private static final String DB_USER = "your_username";
    private static final String DB_PASSWORD = "your_password";
    public static void main(String[] args) {

    }

    public ArrayList<Course> searchByCourseName(String name) throws SQLException{
        Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        ArrayList<Course> courses = new ArrayList<>();
        String sql = 
        "SELECT code, name " + 
        "FROM Courses " +
        "WHERE code = ? OR name = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setString(1, name);
        pstmt.setString(1, name);
            try (ResultSet rst = pstmt.executeQuery()){
                while(rst.next()){
                    String currentName = rst.getString("name");
                    String currentCode = rst.getString("code");
                    Course currentCourse = new Course();
                    courses.add(currentCourse);
                }
            }
        }

        return courses;
    }
}
