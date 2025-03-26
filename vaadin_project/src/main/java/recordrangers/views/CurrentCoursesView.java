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
import com.vaadin.flow.server.VaadinSession;

import recordrangers.models.Course;
import recordrangers.models.User;
import recordrangers.services.CourseDAO;
import recordrangers.services.CourseRegistration;


import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@PageTitle("Course Registration")
@Route(value = "current-courses", layout = StudentHomeView.class)
public class CurrentCoursesView extends CourseSearchView {
    private final Button dropButton = new Button("Drop Course");

    public CurrentCoursesView() throws SQLException{
    	User loggedInUser = (User)VaadinSession.getCurrent().getAttribute("loggedInUser");
    	allCourses = CourseDAO.getEnrolledCourses(loggedInUser.getUserId());
        // Register button logic
        dropButton.addClickListener(e -> {
            Course selected = courseGrid.asSingleSelect().getValue();
            if (selected == null) {
                Notification.show("Please select a course first.");
            } else {
            	try {
					CourseRegistration.dropCourse(loggedInUser.getUserId(), selected.getCourseId());
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
                Notification.show("Dropped: " + selected.getCourseName());
            }
        });

        HorizontalLayout actionsLayout = new HorizontalLayout(getSearchField(),dropButton);
        this.add(actionsLayout);
        
        add(courseGrid);

    }

}
