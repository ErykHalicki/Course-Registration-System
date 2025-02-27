import java.sql.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class UserDAO {
    private Connection connection;

    // Constructor to initialize DB connection
    public UserDAO(Connection connection) {
        this.connection = connection;
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
            stmt.setString(5, user.getProfilePhoto());

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
            stmt.setString(5, user.getProfilePhoto());
            stmt.setInt(6, user.getUserId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating user: " + e.getMessage());
        }
        return false;
    }

    // ================= UNIT TESTS BELOW =================

    private static Connection testConnection;
    private static UserDAO userDAO;

    @BeforeAll
    public static void setupDatabaseConnection() {
        try {
            testConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/your_database", "your_user", "your_password");
            userDAO = new UserDAO(testConnection);
        } catch (SQLException e) {
            fail("Database connection failed: " + e.getMessage());
        }
    }

    @AfterAll
    public static void closeDatabaseConnection() {
        try {
            if (testConnection != null) {
                testConnection.close();
            }
        } catch (SQLException e) {
            System.err.println("Error closing database connection: " + e.getMessage());
        }
    }

    @Test
    public void testCreateUser() {
        User testUser = new User("Alice", "Smith", "alice@example.com", "securepass", "alice.jpg");
        boolean isCreated = userDAO.createUser(testUser);

        assertTrue(isCreated, "User should be created successfully");
        assertTrue(testUser.getUserId() > 0, "User ID should be set after creation");

        // Cleanup: Delete test user
        deleteTestUser(testUser.getUserId());
    }

    @Test
    public void testUpdateUser() {
        // Create test user
        User testUser = new User("Bob", "Jones", "bob@example.com", "mypassword", "bob.jpg");
        assertTrue(userDAO.createUser(testUser), "User should be created successfully");

        // Update user info
        testUser.setFirstName("Robert");
        testUser.setProfilePhoto("new_bob.jpg");
        boolean isUpdated = userDAO.updateUser(testUser);

        assertTrue(isUpdated, "User should be updated successfully");

        // Verify updated data
        try {
            PreparedStatement checkStmt = testConnection.prepareStatement("SELECT first_name, profile_photo FROM User WHERE user_id = ?");
            checkStmt.setInt(1, testUser.getUserId());
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) {
                assertEquals("Robert", rs.getString("first_name"), "First name should be updated");
                assertEquals("new_bob.jpg", rs.getString("profile_photo"), "Profile photo should be updated");
            } else {
                fail("User not found after update");
            }
        } catch (SQLException e) {
            fail("Database error during verification: " + e.getMessage());
        }

        // Cleanup: Delete test user
        deleteTestUser(testUser.getUserId());
    }

    private void deleteTestUser(int userId) {
        try (PreparedStatement stmt = testConnection.prepareStatement("DELETE FROM User WHERE user_id = ?")) {
            stmt.setInt(1, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Failed to delete test user: " + e.getMessage());
        }
    }
}
