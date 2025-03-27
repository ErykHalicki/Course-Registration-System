package recordrangers.views;

import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

import recordrangers.models.User;
import recordrangers.services.UserDAO;

@PageTitle("Profile")
@Route(value = "admin-profile", layout = AdminHomeView.class)
public class AdminProfileView extends VerticalLayout {

    public AdminProfileView() {
    	UserDAO userDAO;
    	try {
            userDAO = new UserDAO();
        } catch (Exception e) {
            Notification.show("Error initializing user service");
            return;
        }
    	User loggedInUser = (User)VaadinSession.getCurrent().getAttribute("loggedInUser");
    	
    	// Profile Photo (Avatar)
    	String first_name = loggedInUser.getFirstName();
    	String last_name = loggedInUser.getLastName();
    	
        Avatar avatar = new Avatar(first_name + " " + last_name);
        // 'frontend/images' folder, then set the path here:
        avatar.setImage("images/profile-photo.png");  // need to figure out how to add images from database

        // A horizontal header with the avatar and the user’s name
        HorizontalLayout headerLayout = new HorizontalLayout(avatar);
        headerLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        headerLayout.setSpacing(true);

        // A FormLayout to display first name, last name, and email
        FormLayout formLayout = new FormLayout();

        TextField firstNameField = new TextField("First Name");
        firstNameField.setValue(loggedInUser.getFirstName());
        firstNameField.setReadOnly(true);

        TextField lastNameField = new TextField("Last Name");
        lastNameField.setValue(loggedInUser.getLastName());
        lastNameField.setReadOnly(true);

        EmailField emailField = new EmailField("Email");
        emailField.setValue(loggedInUser.getEmail());
        emailField.setReadOnly(true);

        
        EmailField newEmailField = new EmailField("Update Email");

        // New Password Fields (left blank by default)
        PasswordField newPasswordField = new PasswordField("New Password");
        PasswordField confirmPasswordField = new PasswordField("Confirm Password");

        // Add the fields to the form
        formLayout.add(firstNameField, lastNameField, emailField, newEmailField, newPasswordField, confirmPasswordField);


        Button saveButton = new Button("Save Changes", event -> {
            // If a new email is provided, use it; otherwise, retain the original email
            String newEmailInput = newEmailField.getValue().trim();
            String newEmail = newEmailInput.isEmpty() ? loggedInUser.getEmail() : newEmailInput;
            String newPassword = newPasswordField.getValue();
            String confirmPassword = confirmPasswordField.getValue();

            // If updating password, validate that both password fields match
            if (!newPassword.isEmpty() || !confirmPassword.isEmpty()) {
                if (!newPassword.equals(confirmPassword)) {
                    Notification.show("Passwords do not match");
                    return;
                }
                // Remember: In production, hash the new password before saving it
            }

            // Update the user's credentials via the DAO method
            if(newPassword != "")
            	loggedInUser.setPassword(newPassword);
            if(newEmail != "")
            	loggedInUser.setEmail(newEmail);
            boolean updated = userDAO.updateUser(loggedInUser);
            if (updated) {
                Notification.show("Profile updated successfully");
                // Update the session attribute with the latest user info
                loggedInUser.setEmail(newEmail);
                if (!newPassword.isEmpty()) {
                    loggedInUser.setPassword(newPassword);
                }
                VaadinSession.getCurrent().setAttribute("loggedInUser", loggedInUser);
                newEmailField.clear();
                newPasswordField.clear();
                confirmPasswordField.clear();
            } else {
                Notification.show("Failed to update profile");
            }
        });

        add(headerLayout, formLayout, saveButton);
        setSpacing(true);
        setPadding(true);
    }
}
