package recordrangers.views;

import java.sql.SQLException;
import java.util.List;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.charts.model.Label;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

import recordrangers.models.Course;
import recordrangers.models.LabSession;
import recordrangers.models.User;
import recordrangers.services.CourseDAO;
import recordrangers.services.LabSessionDAO;
import recordrangers.services.CourseRegistration;
import recordrangers.services.WaitlistDAO;


@PageTitle("Course Registration")
@Route(value = "current-courses", layout = StudentHomeView.class)
public class CurrentCoursesView extends CourseSearchView {
    private final Button dropButton = new Button("Drop Course");
    private Grid<LabSession> labGrid = new Grid<>(LabSession.class); // Grid to display registered lab sessions

    private final Grid<Course> waitlistGrid = new Grid<>(Course.class);

    public CurrentCoursesView() throws SQLException{
    	User loggedInUser = (User)VaadinSession.getCurrent().getAttribute("loggedInUser");
    	allCourses = CourseDAO.getEnrolledCourses(loggedInUser.getUserId());
    	courseGrid.setColumns("courseName", "courseCode", "numCredits", "termLabel", "startDate", "endDate");
        courseGrid.setItems(allCourses);
        updateGrid("");

        // Register button logic
        dropButton.addClickListener(e -> {
            Course selected = courseGrid.asSingleSelect().getValue();
            if (selected == null) {
                Notification.show("Please select a course first.");
            } else {
            	try {
					CourseRegistration.dropCourse(loggedInUser.getUserId(), selected.getCourseId());
					allCourses = CourseDAO.getEnrolledCourses(loggedInUser.getUserId());
					updateGrid("");
					//System.out.println(allCourses);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
                Notification.show("Dropped: " + selected.getCourseName());
            }
        });

        HorizontalLayout actionsLayout = new HorizontalLayout(getSearchField(),dropButton);
        this.add(actionsLayout);
        
        add(courseGrid);
        
        List<LabSession> registeredLabs = LabSessionDAO.getRegisteredLabSections(loggedInUser.getUserId());

        // Create a label for Registered Labs
        Span labLabel = new Span("Registered Lab Sessions");
        labLabel.getElement().getStyle().set("font-weight", "bold");

        // Configure the columns for the Lab Grid (same as before)
        labGrid.setColumns("labName", "capacity", "days", "startTime", "endTime", "location");
        labGrid.setItems(registeredLabs); // Set the registered labs list as the data for the grid

        // Create a layout to hold the label and grid
        VerticalLayout labLayout = new VerticalLayout();
        labLayout.add(labLabel, labGrid);
        labLayout.setPadding(true);

        // Add the lab layout to the main view
        add(labLayout);
        
        List<Course> waitlistedCourses = WaitlistDAO.getWaitlistedCourses(loggedInUser.getUserId());
        Span  waitlistLabel = new Span ("Waitlisted Courses");
        waitlistLabel.getElement().getStyle().set("font-weight", "bold");
       
        waitlistGrid.setColumns("courseName", "courseCode", "numCredits", "termLabel");
        waitlistGrid.setItems(waitlistedCourses);

        VerticalLayout waitlistLayout = new VerticalLayout();
        waitlistLayout.add(waitlistLabel);
        waitlistLayout.add(waitlistGrid);
        waitlistLayout.setPadding(true);

        add(waitlistLayout);
        
        
    }

}
