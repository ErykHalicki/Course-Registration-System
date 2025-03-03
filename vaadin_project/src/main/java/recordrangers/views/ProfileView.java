package recordrangers.views;

import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Profile")
@Route(value = "profile", layout = MainLayout.class)
public class ProfileView extends VerticalLayout {

    public ProfileView() {

        // Profile Photo (Avatar)
        Avatar avatar = new Avatar("John Doe"); 
        // 'frontend/images' folder, then set the path here:
        avatar.setImage("images/profile-photo.png");  // need to figure out how to add images from database

        // A horizontal header with the avatar and the user’s name
        HorizontalLayout headerLayout = new HorizontalLayout(avatar);
        headerLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        headerLayout.setSpacing(true);

        // A FormLayout to display first name, last name, and email
        FormLayout formLayout = new FormLayout();

        TextField firstNameField = new TextField("First Name");
        firstNameField.setValue("John");
        firstNameField.setReadOnly(true);

        TextField lastNameField = new TextField("Last Name");
        lastNameField.setValue("Doe");
        lastNameField.setReadOnly(true);

        EmailField emailField = new EmailField("Email");
        emailField.setValue("johndoe@example.com");
        emailField.setReadOnly(true);

        // Add the fields to the form
        formLayout.add(firstNameField, lastNameField, emailField);

        // Optional: Adjust how many columns the form uses
        // formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP));

        // Add components to the main layout
        add(headerLayout, formLayout);

        // Optional styling or spacing
        setSpacing(true);
        setPadding(true);
    }
}
