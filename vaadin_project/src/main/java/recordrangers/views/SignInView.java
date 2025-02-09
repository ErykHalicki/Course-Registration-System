package recordrangers.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.Route;

@Route("signin") // This sets the route to access this page, e.g., http://localhost:8080/signin
public class SignInView extends VerticalLayout {

    public SignInView() {
        // Create a TextField for email input
        TextField emailField = new TextField("Email");

        // Create a PasswordField for password input
        PasswordField passwordField = new PasswordField("Password");

        // Create a button for submitting the form
        Button signInButton = new Button("Sign In", event -> {
            // Placeholder for form submission logic
            String email = emailField.getValue();
            String password = passwordField.getValue();
            // Display the entered credentials as a Notification (just for demo purposes)
            Notification.show("Email: " + email + " Password: " + password);
        });

        // Add the components to the layout
        FormLayout formLayout = new FormLayout();
        formLayout.add(emailField, passwordField, signInButton);

        // Add the form layout to the view
        add(formLayout);
    }
}