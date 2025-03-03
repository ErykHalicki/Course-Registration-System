import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import java.sql.*;
import java.util.Arrays;

public class UserDAOTest{
    private static Connection connection;
    private static UserDAO userDAO;
    
    @BeforeAll
    static void setup() throws SQLException {
        // Use H2 in-memory database
        connection = DriverManager.getConnection("jdbc:h2:mem:testdb", "sa", "");
        userDAO = new UserDAO(connection);

        // Create users table
        Statement stmt = connection.createStatement();
        stmt.execute("CREATE TABLE users (user_id INT AUTO_INCREMENT PRIMARY KEY, first_name VARCHAR(50), last_name VARCHAR(50), email VARCHAR(100) UNIQUE, password VARCHAR(255), profile_picture VARBINARY(1024), time_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP, time_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP)");
        stmt.close();
    }

    @AfterAll
    static void teardown() throws SQLException {
        connection.close();
    }

    @Test
    void testCreateUser() throws SQLException {
        User user = new User(0, "John", "Doe", "john@example.com", "securepassword",User.UserType.ADMIN, new byte[]{1, 2, 3}, null, null);
        boolean isCreated = userDAO.createUser(user);
        assertTrue(isCreated, "User should be successfully created");
    }

    @Test
    void testGetUserById() throws SQLException {
        User user = new User(0, "Alice", "Smith", "alice@example.com", "password123",User.UserType.STUDENT, new byte[]{4, 5, 6}, null, null);
        userDAO.createUser(user);
        
        User retrievedUser = userDAO.getUserById(user.getUserId());
        assertNotNull(retrievedUser, "User should be retrieved");
        assertEquals("Alice", retrievedUser.getFirstName(), "First name should match");
        assertTrue(Arrays.equals(new byte[]{4, 5, 6}, retrievedUser.getProfilePicture()), "Profile picture should match");
    }

    @Test
    void testUpdateUser() throws SQLException {
        User user = new User(0, "Jane", "Doe", "jane@example.com", "oldpassword",User.UserType.ADMIN,new byte[]{7, 8, 9}, null, null);
        userDAO.createUser(user);

        // Update user details
        user.setLastName("UpdatedDoe");
        user.setPassword("newpassword");
        boolean isUpdated = userDAO.updateUser(user);
        assertTrue(isUpdated, "User should be updated");

        // Fetch updated user
        User updatedUser = userDAO.getUserById(user.getUserId());
        assertEquals("UpdatedDoe", updatedUser.getLastName(), "Last name should be updated");
    }
}

