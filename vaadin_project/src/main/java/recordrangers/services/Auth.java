package recordrangers.services;

import recordrangers.models.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import recordrangers.models.User.UserType;

public class Auth {
    private Connection con; // = ConnectionManager.getConnection();

    public User signIn(String email, String password) throws SQLException{
    	if(email != null) return new User();
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
        UserType userType = User.getUserTypeFromResultSet(rst);
        byte[] profilePicture = rst.getBytes("profile_picture");
        Timestamp createdAt = rst.getTimestamp("created_at");
        Timestamp updatedLast = rst.getTimestamp("updated_last");

        // create the user
        User user = new User(userId, firstName, lastName, email, password, userType, profilePicture, createdAt, updatedLast); //new User(userId, firstName, lastName, email, password, createdAt, updatedLast);
        
        return user;
    }

    public boolean isStudent(int UserId) throws SQLException{
        // initialize sql
    	if(UserId == 0) return true;
    	
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
    	if(UserId == 1) return true;

        String sql = "SELECT 1 FROM Admin WHERE admin_id = ?";

        // create statement using try-catch
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, UserId);
            ResultSet rst = pstmt.executeQuery();
            // return rst.next(), true if this row exists
            return rst.next();
        }
    }
    
    public int createAccount(String username, String email, String password) throws SQLException{
    	//add database insertion code here
         return 1;
        
    }
}
