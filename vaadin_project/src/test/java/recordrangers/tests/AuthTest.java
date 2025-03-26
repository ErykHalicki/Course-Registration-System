package recordrangers.tests;
import java.sql.SQLException;
import recordrangers.services.Auth;
import recordrangers.models.User;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class AuthTest {
        
    @BeforeAll
    public static void init() {

    }

    @Test
    public void testLogin() throws SQLException {
        String firstName = "John";
        String lastName = "Doe";
        String email = "john.doe@example.com";
        String password = "password123";
        
        User result = Auth.signIn(email, password);
        assertEquals(firstName, result.getFirstName());
        assertEquals(lastName, result.getLastName());
    }

    @Test
    public void testWrongCredentials() throws SQLException {
        String firstName = "John";
        String lastName = "Doe";
        String email = "john.doe@example.com";
        String password = "wrongPassword";
    
        User result = Auth.signIn(email, password);
        assertNull(result);
    }

    @Test
    public void testIsStudent() throws SQLException {
        String email = "john.doe@example.com";
        String password = "password123";
        User user = Auth.signIn(email, password);
        assertTrue(Auth.isStudent(user.getUserId()));
    }

    @Test
    public void testIsStudentNot() throws SQLException {
        String email = "jane.smith@example.com";
        String password = "password456";
        User user = Auth.signIn(email, password);
        assertFalse(Auth.isStudent(user.getUserId()));
    }

    @Test
    public void testIsAdmin() throws SQLException {
        String email = "jane.smith@example.com";
        String password = "password456";
        User user = Auth.signIn(email, password);
        assertTrue(Auth.isAdmin(user.getUserId()));
    }

    @Test
    public void testIsAdminNot() throws SQLException {
        String email = "john.doe@example.com";
        String password = "password123";
        User user = Auth.signIn(email, password);
        assertFalse(Auth.isAdmin(user.getUserId()));
    }
}
