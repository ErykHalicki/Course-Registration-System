package recordrangers.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import recordrangers.models.User;
import recordrangers.models.User.UserType;

public class Auth{
	
    private static Connection con;

    static {
        try {
            con = DatabaseConnection.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize database connection", e);
        }
    }
    
    
    public static User signIn(String email, String password) throws SQLException{
    	con =  DatabaseConnection.getInstance().getConnection();
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

    public static User ResultSetToUser(ResultSet rst) throws SQLException{
        // get attributes
    	rst.next();
        int userId = rst.getInt("user_id");
        String firstName = rst.getString("first_name");
        String lastName = rst.getString("last_name");
        String email = rst.getString("email");
        String password = rst.getString("password");
        UserType userType;
        if(rst.getString("user_type").equals("Student")) userType = User.UserType.STUDENT;
        else userType = User.UserType.ADMIN;
        String profilePicture = rst.getString("profile_photo");
        Timestamp createdAt = rst.getTimestamp("created_at");
        Timestamp updatedLast = rst.getTimestamp("updated_last");

        // create the user
        User user = new User(userId, firstName, lastName, email, password, userType, profilePicture, createdAt, updatedLast); //new User(userId, firstName, lastName, email, password, createdAt, updatedLast);
        
        return user;
    }

    public static boolean isStudent(int UserId) throws SQLException{
        // initialize sql
    	//if(UserId == 0) return true;
    	con = DatabaseConnection.getInstance().getConnection();
        String sql = "SELECT 1 FROM Student WHERE student_id = ?";

        // create statement using try-catch
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, UserId);
            ResultSet rst = pstmt.executeQuery();
            // return rst.next(), true if this row exists
            return rst.next();
        }
        catch(SQLException ex) {
        	return false;
        }
    }
    public static boolean isAdmin(int UserId) throws SQLException{
        // initialize sql
    	//if(UserId == 1) return true;
    	con = DatabaseConnection.getInstance().getConnection();
        String sql = "SELECT 1 FROM Admin WHERE admin_id = ?";

        // create statement using try-catch
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, UserId);
            ResultSet rst = pstmt.executeQuery();
            // return rst.next(), true if this row exists
            return rst.next();
        }
        catch(SQLException ex) {
        	return false;
        }
    }
    
    public int createAccount(String username, String email, String password) throws SQLException{
    	Connection con = DatabaseConnection.getInstance().getConnection();
        
        // First, check if the email already exists
        String checkEmailQuery = "SELECT COUNT(*) FROM User WHERE email = ?";
        try (PreparedStatement checkStmt = con.prepareStatement(checkEmailQuery)) {
            checkStmt.setString(1, email);
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    // Email already exists
                    return -1;
                }
            }
        }
        
        // Prepare the insert query
        String insertQuery = "INSERT INTO User " +
                "(first_name, last_name, email, password, profile_photo, " +
                "created_at, updated_last, user_type) " +
                "VALUES (?, ?, ?, ?, ?, " +
                "CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, ?)";

			try (PreparedStatement pstmt = con.prepareStatement(insertQuery, PreparedStatement.RETURN_GENERATED_KEYS)) {
			// Set parameters
			pstmt.setString(1, username);
			
			// Generate a random last name
			String lastName = "lastname";
			pstmt.setString(2, lastName);
			
			pstmt.setString(3, email);
			pstmt.setString(4, password);
			
			// Add a default profile photo URL or path
			String defaultProfilePhoto = "https://example.com/default-profile";
			pstmt.setString(5, defaultProfilePhoto);
			
			// User type remains the same
			pstmt.setString(6, "Student");
			
			// Execute the insert
			int affectedRows = pstmt.executeUpdate();
			
			if (affectedRows == 0) {
			   throw new SQLException("Creating user failed, no rows affected.");
			}
            
            // Retrieve the auto-generated user_id
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int userId = generatedKeys.getInt(1);
                    
                    // Optional: Insert into Student table
                    String insertStudentQuery = "INSERT INTO Student " +
                                                "(student_id, enrollment_date, status) " +
                                                "VALUES (?, CURRENT_DATE, 'Active')";
                    try (PreparedStatement studentStmt = con.prepareStatement(insertStudentQuery)) {
                        studentStmt.setInt(1, userId);
                        studentStmt.executeUpdate();
                    }
                    
                    return userId;
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
        }
    }
}
