import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AuthTest {
    private Auth auth;
    
    @Before
    public void init() {
        auth = new Auth();
    }

    @Test
    public void testLogin() throws SQLException {
        String firstName = "John";
        String lastName = "Doe";
        String email = "john.doe@example.com";
        String password = "password123";
        
        User result = auth.signIn(email, password);
        Assert.assertEquals(firstName, result.getFirstName());
        Assert.assertEquals(lastName, result.getLastName());
    }

    @Test
    public void testWrongCredentials() throws SQLException {
        String firstName = "John";
        String lastName = "Doe";
        String email = "john.doe@example.com";
        String password = "wrongPassword";
    
        User result = auth.signIn(email, password);
        Assert.assertNull(result);
    }

    @Test
    public void testIsStudent() throws SQLException {
        String email = "john.doe@example.com";
        String password = "password123";
        User user = auth.signIn(email, password);
        Assert.assertTrue(auth.isStudent(user.getUserId()));
    }

    @Test
    public void testIsStudentNot() throws SQLException {
        String email = "jane.smith@example.com";
        String password = "password456";
        User user = auth.signIn(email, password);
        Assert.assertFalse(auth.isStudent(user.getUserId()));
    }

    @Test
    public void testIsAdmin() throws SQLException {
        String email = "jane.smith@example.com";
        String password = "password456";
        User user = auth.signIn(email, password);
        Assert.assertTrue(auth.isAdmin(user.getUserId()));
    }

    @Test
    public void testIsAdminNot() throws SQLException {
        String email = "john.doe@example.com";
        String password = "password123";
        User user = auth.signIn(email, password);
        Assert.assertFalse(auth.isAdmin(user.getUserId()));
    }
}
