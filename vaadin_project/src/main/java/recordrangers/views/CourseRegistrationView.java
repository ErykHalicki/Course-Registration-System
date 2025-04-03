package recordrangers.views;

import java.sql.SQLException;
import java.util.ArrayList;


import com.vaadin.flow.component.button.Button;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

import recordrangers.models.Course;
import recordrangers.models.LabSession;
import recordrangers.models.User;
import recordrangers.services.CourseDAO;
import recordrangers.services.CourseRegistration;
import recordrangers.services.LabRegistration;
import recordrangers.services.LabSessionDAO;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@PageTitle("Course Registration")
@Route(value = "register", layout = StudentHomeView.class)
public class CourseRegistrationView extends CourseSearchView {
    private final Button registerButton = new Button("Register");
    private final Button registerLabButton = new Button("Register for Lab");
    private final Grid<LabSession> labGrid = new Grid<>(LabSession.class);
    public CourseRegistrationView() throws SQLException{

        // Register button logic
        registerButton.addClickListener(e -> {
            Course selected = courseGrid.asSingleSelect().getValue();
            if (selected == null) {
                Notification.show("Please select a course first.");
            } else {
                // Here you'd normally insert a record in a "registration" table
                // with the user info and the course ID.
            	User loggedInUser = (User)VaadinSession.getCurrent().getAttribute("loggedInUser");
            	try {
					CourseRegistration.registerStudent(loggedInUser.getUserId(), selected.getCourseId());
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
                Notification.show("Registered for: " + selected.getCourseName());
            }
        });
        
        

        HorizontalLayout actionsLayout = new HorizontalLayout(getSearchField(),registerButton,registerLabButton);
        this.add(actionsLayout);
        
        add(courseGrid);
        User loggedInUser = (User) VaadinSession.getCurrent().getAttribute("loggedInUser");
        List<LabSession> labSections = LabSessionDAO.getAllLabSessions();
        labGrid.setColumns("labName", "capacity", "days", "startTime", "endTime", "location");
        labGrid.setItems(labSections);

        
        registerLabButton.addClickListener(e -> {
            LabSession selected = labGrid.asSingleSelect().getValue();
            if (selected == null) {
                Notification.show("Please select a course first.");
            } else {
                // Here you'd normally insert a record in a "registration" table
                // with the user info and the course ID.
            	try {
					LabRegistration.registerStudentIntoLab(loggedInUser.getUserId(), selected.getSectionId());
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
                Notification.show("Registered for: " + selected.getLabName());
            }
        });
        
        Span labLabel = new Span("Available Lab Sections");
        labLabel.getElement().getStyle().set("font-weight", "bold");

        VerticalLayout labLayout = new VerticalLayout();
        labLayout.add(labLabel, labGrid);
        labLayout.setPadding(true);

        add(labLayout);
    }

}
