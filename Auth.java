import java.sql.*;

public class Auth {
    private final Connection con = ConnectionManager.getConnection();
    public static void main(String[] args) {
       
    }

   

    public User signIn(String email, String password) throws SQLException{
        String query = 
        "SELECT * " + 
        "FROM users " +
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
        User user = new User();
        int userId = rst.getInt("user_id");
        String userName = rst.getString("username");
        String password = rst.getString("password");
        String role = rst.getString("role");
        byte[] profilePicture = rst.getBytes("profile_picture");

        // Include all attributes when table in db is completed
        user.setUserId(userId);
        user.setUserName(userName);
        user.setPassword(password);
        user.setRole(role);
        user.setProfilePicture(profilePicture);
        return user;
    }
}
