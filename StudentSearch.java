import java.sql.*;
import java.util.ArrayList;
public class StudentSearch {
    private final Connection con = ConnectionManager.getConnection();

    public static void main(String[] args) {

    }

    public ArrayList<Student> searchStudentsByName(String firstName, String lastName) throws SQLException {
        ArrayList<Student> students = new ArrayList<>();
        String sql = 
        "SELECT * " +
        "FROM Students " + 
        "WHERE firstName = ? AND lastName = ?";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            // To Do: allow for matching of 1 name or both
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            try (ResultSet rst = pstmt.executeQuery()) {
                while (rst.next()) {
                    students.add(new Student());
                }
            }
        }
        return students;
    }
}
