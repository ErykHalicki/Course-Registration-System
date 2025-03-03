import java.sql.*;

public class UserDAO extends User {
    private static Connection connection;

    public UserDAO() throws SQLException{
            UserDAO.connection = DatabaseConnection.getConnection();
        }
    
    public UserDAO(Connection connection) {
            UserDAO.connection = connection;
        }
    
    public User getUserById(int userId) {
            String query = "SELECT * FROM users WHERE user_id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User( rs.getInt("user_id")   , rs.getString("first_name") , rs.getString("last_name") , rs.getString("email") , rs.getString("password"), User.UserType.valueOf(rs.getString("user_Type")), rs.getBytes("profile_picture") , rs.getTimestamp("time_created") , rs.getTimestamp("time_updated") );}
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
        }
    // Method to create a new user
    public boolean createUser(User user) {
        String sql = "INSERT INTO User (first_name, last_name, email, password, profile_photo, created_at, updated_last) " +
                     "VALUES (?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, user.getFirstName());
            stmt.setString(2, user.getLastName());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPassword()); // Hash before storing in a real app
            stmt.setBytes(5, user.getProfilePicture());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                // Retrieve generated user ID
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        user.setUserId(generatedKeys.getInt(1));
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error inserting user: " + e.getMessage());
        }
        return false;
    }

    // Method to update user information
    public boolean updateUser(User user) {
        String sql = "UPDATE User SET first_name = ?, last_name = ?, email = ?, password = ?, profile_photo = ?, updated_last = CURRENT_TIMESTAMP " +
                     "WHERE user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user.getFirstName());
            stmt.setString(2, user.getLastName());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPassword()); // Hash before storing in a real app
            stmt.setBytes(5, user.getProfilePicture());
            stmt.setInt(6, user.getUserId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating user: " + e.getMessage());
        }
        return false;
    }

}
