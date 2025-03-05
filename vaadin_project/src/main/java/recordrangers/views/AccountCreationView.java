package recordrangers.views;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.dialog.Dialog;

import java.sql.SQLException;

import recordrangers.models.User;
import recordrangers.services.Auth;

@Route("create-account")
public class AccountCreationView extends Composite<VerticalLayout> {

    public AccountCreationView() {
        VerticalLayout layout = getContent();
        layout.setSizeFull();
        layout.setAlignItems(Alignment.CENTER);
        layout.setJustifyContentMode(JustifyContentMode.CENTER);

        VerticalLayout formLayout = new VerticalLayout();
        formLayout.setWidth("400px");
        formLayout.setSpacing(true);
        formLayout.setPadding(true);
        formLayout.setAlignItems(Alignment.CENTER);
        formLayout.getStyle().set("box-shadow", "0 2px 10px rgba(0,0,0,0.1)")
                              .set("border-radius", "8px")
                              .set("background-color", "var(--lumo-base-color)");

        H1 title = new H1("Create an Account");

        TextField usernameField = new TextField("Username");
        usernameField.setWidthFull();

        EmailField emailField = new EmailField("Email");
        emailField.setWidthFull();

        PasswordField passwordField = new PasswordField("Password");
        passwordField.setWidthFull();

        PasswordField confirmPasswordField = new PasswordField("Confirm Password");
        confirmPasswordField.setWidthFull();

        Button createAccountButton = new Button("Create Account");
        createAccountButton.setWidthFull();

        // Link back to login page
        Anchor loginLink = new Anchor("", "Back to Login");
        loginLink.getElement().getStyle().set("margin-top", "10px");

        formLayout.add(title, usernameField, emailField, passwordField, confirmPasswordField, createAccountButton, loginLink);
        layout.add(formLayout);

        createAccountButton.addClickListener(event -> {
            String username = usernameField.getValue();
            String email = emailField.getValue();
            String password = passwordField.getValue();
            String confirmPassword = confirmPasswordField.getValue();

            if (!password.equals(confirmPassword)) {
                Notification.show("Passwords do not match!", 3000, Notification.Position.MIDDLE);
                return;
            }

            Auth auth = new Auth();
            try {
                int userId = auth.createAccount(username, email, password);
                if (userId != -1) {
                    Notification.show("Account created successfully! Please log in.", 3000, Notification.Position.MIDDLE);
                    getUI().ifPresent(ui -> ui.navigate(""));
                } else {
                    Notification.show("Account creation failed. Please try again.", 3000, Notification.Position.MIDDLE);
                }
            } catch (SQLException e) {
                Notification.show("An error occurred: " + e.getMessage(), 5000, Notification.Position.MIDDLE);
            }
        });
    }
}