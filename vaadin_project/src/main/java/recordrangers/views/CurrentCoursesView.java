package recordrangers.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import recordrangers.models.Course;

import java.util.ArrayList;
import java.util.List;

@PageTitle("Current Courses")
@Route(value = "current-courses", layout = StudentHomeView.class)
public class CurrentCoursesView extends VerticalLayout {

    private final Grid<Course> courseGrid;
    private final List<Course> enrolledCourses;

    public CurrentCoursesView() {
        // 1) Create some dummy data for demonstration
        enrolledCourses = new ArrayList<>();
        // 2) Initialize the grid and configure columns
        courseGrid = new Grid<>(Course.class, false);
        courseGrid.addColumn(Course::getCourseName).setHeader("Course Name");
        courseGrid.addColumn(Course::getCourseCode).setHeader("Course Code");
        courseGrid.addColumn(Course::getNumCredits).setHeader("Credits");
        courseGrid.addColumn(Course::getTermLabel).setHeader("Term");
        courseGrid.addColumn(Course::getStartDate).setHeader("Start Date");
        courseGrid.addColumn(Course::getEndDate).setHeader("End Date");

        // 3) Add a column with a Drop button to remove courses from the grid
        courseGrid.addComponentColumn(course -> {
            Button dropButton = new Button("Drop", event -> {
                // Remove the selected course from the in-memory list
                enrolledCourses.remove(course);
                // Refresh the grid so it no longer displays the dropped course
                courseGrid.getDataProvider().refreshAll();
            });
            return dropButton;
        }).setHeader("Actions");

        // 4) Populate the grid with the dummy list
        courseGrid.setItems(enrolledCourses);

        // 5) Add the grid to the layout
        add(courseGrid);
    }
}
