package recordrangers.views;

import java.sql.SQLException;
import java.util.ArrayList;


import com.vaadin.flow.component.button.Button;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import recordrangers.models.Course;

import recordrangers.services.CourseDAO;


import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@PageTitle("Course Registration")
@Route(value = "register", layout = StudentHomeView.class)
public class CourseRegistrationView extends CourseSearchView {
    private final Button registerButton = new Button("Register");

    public CourseRegistrationView() throws SQLException{

        // Register button logic
        registerButton.addClickListener(e -> {
            Course selected = courseGrid.asSingleSelect().getValue();
            if (selected == null) {
                Notification.show("Please select a course first.");
            } else {
                // Here you'd normally insert a record in a "registration" table
                // with the user info and the course ID.
                Notification.show("Registered for: " + selected.getCourseName());
            }
        });

        HorizontalLayout actionsLayout = new HorizontalLayout(getSearchField(),registerButton);
        this.add(actionsLayout);
        
        add(courseGrid);

    }

}
