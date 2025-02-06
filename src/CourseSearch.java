import java.sql.*;
import java.util.ArrayList;
public class CourseSearch {
    private final Connection con = ConnectionManager.getConnection();

    public static void main(String[] args) {

    }

    public ArrayList<Course> searchByCourseName(String name) throws SQLException{
        ArrayList<Course> courses = new ArrayList<>();
        String sql = 
        "SELECT code, name " + 
        "FROM Courses " +
        "WHERE code = ? OR name = ?";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
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
