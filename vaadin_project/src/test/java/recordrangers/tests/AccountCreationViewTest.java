package recordrangers.tests;

import recordrangers.views.AccountCreationView;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;



import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

public class AccountCreationViewTest {

    private AccountCreationView view;

    @BeforeEach
    void setUp() {
        view = new AccountCreationView();
    }

    @Test
    void initialLayoutIsCorrect() {
        VerticalLayout layout = view.getContent();
        assertEquals(1, layout.getComponentCount(), "There should be exactly one main form layout");

        VerticalLayout formLayout = (VerticalLayout) layout.getComponentAt(0);
        assertEquals(7, formLayout.getComponentCount(), "Form layout should contain 7 components (title, fields, button, link)");
    }

    @Test
    void allFieldsInitiallyEmpty() {
        TextField usernameField = getTextField("Username");
        EmailField emailField = getEmailField("Email");
        PasswordField passwordField = getPasswordField("Password");
        PasswordField confirmPasswordField = getPasswordField("Confirm Password");

        assertTrue(usernameField.isEmpty());
        assertTrue(emailField.isEmpty());
        assertTrue(passwordField.isEmpty());
        assertTrue(confirmPasswordField.isEmpty());
    }

    private TextField getTextField(String label) {
        return (TextField) getComponentByLabel(label);
    }

    private EmailField getEmailField(String label) {
        return (EmailField) getComponentByLabel(label);
    }

    private PasswordField getPasswordField(String label) {
        return (PasswordField) getComponentByLabel(label);
    }

    private Button getButton(String label) {
        return (Button) getComponentByLabel(label);
    }

    private com.vaadin.flow.component.Component getComponentByLabel(String label) {
        VerticalLayout formLayout = (VerticalLayout) view.getContent().getComponentAt(0);

        return formLayout.getChildren()
            .filter(component -> {
                if (component instanceof TextField textField) {
                    return label.equals(textField.getLabel());
                } else if (component instanceof EmailField emailField) {
                    return label.equals(emailField.getLabel());
                } else if (component instanceof PasswordField passwordField) {
                    return label.equals(passwordField.getLabel());
                } else if (component instanceof Button button) {
                    return label.equals(button.getText());
                }
                return false;
            })
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Component with label '" + label + "' not found"));
    }
}