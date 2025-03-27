package recordrangers.tests;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasLabel;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.server.VaadinSession;
import org.junit.Before;
import org.junit.Test;
import recordrangers.models.User;
import recordrangers.views.ProfileView;

import static org.junit.Assert.*;

public class ProfileViewIntegrationTest {

    private ProfileView profileView;
    private User realUser;

    @Before
    public void setUp() throws Exception {
        // Create a real user with existing credentials.
        realUser = new User();
        realUser.setUserId(1); // Assumes the user with ID 1 exists.
        realUser.setFirstName("John");
        realUser.setLastName("Doe");
        realUser.setEmail("john.doe@example.com");
        realUser.setPassword("password123");

        // Set up Vaadin session and UI .
        VaadinSession session = VaadinSession.getCurrent();
        if (session == null) {
            
            session = new VaadinSession(null);
            VaadinSession.setCurrent(session);
        }
        session.setAttribute("loggedInUser", realUser);

        UI ui = UI.getCurrent();
        if (ui == null) {
            ui = new UI();
            UI.setCurrent(ui);
        }

        // Instantiate the ProfileView (which will create a real UserDAO)
        profileView = new ProfileView();
    }

    @Test
    public void testInitialDisplay() {
        // Verify that the read-only fields display the correct first name, last name, and current email.

        TextField firstNameField = findComponentByLabel(profileView, "First Name", TextField.class);
        assertNotNull("First Name field should be present", firstNameField);
        assertEquals("John", firstNameField.getValue());

        TextField lastNameField = findComponentByLabel(profileView, "Last Name", TextField.class);
        assertNotNull("Last Name field should be present", lastNameField);
        assertEquals("Doe", lastNameField.getValue());

        EmailField currentEmailField = findComponentByLabel(profileView, "Current Email", EmailField.class);
        assertNotNull("Current Email field should be present", currentEmailField);
        assertEquals("john.doe@example.com", currentEmailField.getValue());
    }

    @Test
    public void testUpdateCredentialsAndFieldClearance() {
        // Find the fields for new email, new password, and confirm password.
        EmailField newEmailField = findComponentByLabel(profileView, "New Email", EmailField.class);
        PasswordField newPasswordField = findComponentByLabel(profileView, "New Password", PasswordField.class);
        PasswordField confirmPasswordField = findComponentByLabel(profileView, "Confirm Password", PasswordField.class);
        assertNotNull("New Email field should exist", newEmailField);
        assertNotNull("New Password field should exist", newPasswordField);
        assertNotNull("Confirm Password field should exist", confirmPasswordField);

        // Enter new credentials.
        newEmailField.setValue("newjohn.doe@example.com");
        newPasswordField.setValue("password456");
        confirmPasswordField.setValue("password456");

        // Locate and click the "Save Changes" button.
        Button saveButton = findComponentByText(profileView, "Save Changes", Button.class);
        assertNotNull("Save Changes button should exist", saveButton);
        saveButton.click();

        // After clicking "Save Changes", the new email and password fields should be cleared.
        assertTrue("New Email field should be cleared", newEmailField.isEmpty());
        assertTrue("New Password field should be cleared", newPasswordField.isEmpty());
        assertTrue("Confirm Password field should be cleared", confirmPasswordField.isEmpty());

        // Verify that the loggedInUser session attribute was updated.
        User updatedUser = (User) VaadinSession.getCurrent().getAttribute("loggedInUser");
        assertEquals("newjohn.doe@example.com", updatedUser.getEmail());
        assertEquals("password456", updatedUser.getPassword());
    }

    // Helper method to recursively search for a component by its label.
    private <T extends Component & HasValue<?, ?>> T findComponentByLabel(Component container, String label, Class<T> clazz) {
        if (clazz.isInstance(container)) {
            T component = clazz.cast(container);
            if (((HasLabel) component).getLabel() != null && ((HasLabel) component).getLabel().equals(label)) {
                return component;
            }
        }
        for (Component child : container.getChildren().toList()) {
            T result = findComponentByLabel(child, label, clazz);
            if (result != null) {
                return result;
            }
        }
        return null;
    }

    // Helper method to recursively search for a button by its text.
    private <T extends Component> T findComponentByText(Component container, String text, Class<T> clazz) {
        if (clazz.isInstance(container)) {
            T component = clazz.cast(container);
            if (component instanceof Button) {
                Button button = (Button) component;
                if (button.getText() != null && button.getText().equals(text)) {
                    return component;
                }
            }
        }
        for (Component child : container.getChildren().toList()) {
            T result = findComponentByText(child, text, clazz);
            if (result != null) {
                return result;
            }
        }
        return null;
    }
}
