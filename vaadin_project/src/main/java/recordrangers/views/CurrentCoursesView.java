package recordrangers.views;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import recordrangers.models.Course;

import java.util.List;

@PageTitle("Current Courses")
@Route(value = "current-courses", layout = MainLayout.class)
public class CurrentCoursesView extends VerticalLayout {

    public CurrentCoursesView() {
        // Set some spacing/padding so the content doesn't hug the edges
        setSpacing(true);
        setPadding(true);

        addClassName("current-courses-view");

        // Add a heading at the top
        H2 heading = new H2("My Current Courses");
        add(heading);

        // Create the Grid without auto-generated columns
        Grid<Course> courseGrid = new Grid<>(Course.class, false);
        // Define columns manually
        courseGrid.addColumn(Course::getCourseName)
                  .setHeader("Course Name")
                  .setAutoWidth(true);

        // display more details once database setup:
        courseGrid.addColumn(Course::getMaxCapacity)
                  .setHeader("Max Capacity")
                  .setAutoWidth(true);
        courseGrid.addColumn(Course::getEnrollment)
                  .setHeader("Enrolled")
                  .setAutoWidth(true);

        // Add a little style to the grid (striped rows, column borders, etc.)
        courseGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES, 
                                    GridVariant.LUMO_COLUMN_BORDERS);

        // Example data
        List<Course> enrolledCourses = List.of(
                new Course("MATH101", 30, 25),
                new Course("PHYS201", 40, 38)
        );

        courseGrid.setItems(enrolledCourses);

        // Make the grid take full available width
        courseGrid.setWidthFull();

        // Add the grid to the layout
        add(courseGrid);

        // Optionally set the entire view to use the full size of the browser
        setSizeFull();
        // And make the grid grow to fill remaining space
        courseGrid.setSizeFull();
    }
}
