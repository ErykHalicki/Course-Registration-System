package recordrangers.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Auth {
    private Connection con; // = ConnectionManager.getConnection();

    public User signIn(String email, String password) throws SQLException{
        String query = 
        "SELECT * " + 
        "FROM User " +
        "WHERE email = ? AND password = ?";

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            try (ResultSet results = pstmt.executeQuery()) {
                if (!results.isBeforeFirst()) {
                    return null;
                }
                return ResultSetToUser(results);
            }
        }
    }

    public User ResultSetToUser(ResultSet rst) throws SQLException{
        // get attributes
        int userId = rst.getInt("user_id");
        String firstName = rst.getString("first_name");
        String lastName = rst.getString("last_name");
        String email = rst.getString("email");
        String password = rst.getString("password");
        String createdAt = rst.getString("created_at");
        String updatedLast = rst.getString("updated_last");

        // create the user
        User user = new User(); //new User(userId, firstName, lastName, email, password, createdAt, updatedLast);
        
        return user;
    }

    public boolean isStudent(int UserId) throws SQLException{
        // initialize sql
        String sql = "SELECT 1 FROM Student WHERE student_id = ?";

        // create statement using try-catch
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, UserId);
            ResultSet rst = pstmt.executeQuery();
            // return rst.next(), true if this row exists
            return rst.next();
        }
    }
    public boolean isAdmin(int UserId) throws SQLException{
        // initialize sql
        String sql = "SELECT 1 FROM Admin WHERE admin_id = ?";

        // create statement using try-catch
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, UserId);
            ResultSet rst = pstmt.executeQuery();
            // return rst.next(), true if this row exists
            return rst.next();
        }
    }
}
