package recordrangers.views;

import recordrangers.models.Course;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.vaadin.example.models.Course;

import java.util.List;

/**
 * A sample Vaadin view class.
 * <p>
 * To implement a Vaadin view just extend any Vaadin component and use @Route
 * annotation to announce it in a URL as a Spring managed bean.
 * <p>
 * A new instance of this class is created for every new user and every browser
 * tab/window.
 * <p>
 * The main view contains a text field for getting the user name and a button
 * that shows a greeting message in a notification.
 */
@Route
public class MainView extends VerticalLayout {

    /**
     * Construct a new Vaadin view.
     * <p>
     * Build the initial UI state for the user accessing the application.
     *
     * @param service
     *            The message service. Automatically injected Spring managed bean.
     */
    public MainView() {

    	Grid<Course> courseGrid = new Grid<>(Course.class);
        
        // Define columns (you can also customize column headers)
        courseGrid.setColumns("courseName", "maxCapacity", "enrollment");
        courseGrid.getColumnByKey("courseName").setHeader("Course Name");
        courseGrid.getColumnByKey("maxCapacity").setHeader("Capacity");
        courseGrid.getColumnByKey("enrollment").setHeader("Enrolled");

        // Sample data
        List<Course> courses = List.of(
            new Course("MATH101", 30, 25),
            new Course("PHYS201", 40, 38),
            new Course("COSC305", 50, 45)
        );

        // Set grid items
        courseGrid.setItems(courses);

        // Add grid to the layout
        add(courseGrid);
    }
}
