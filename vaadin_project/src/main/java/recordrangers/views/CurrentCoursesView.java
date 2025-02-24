package recordrangers.views;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import recordrangers.models.Course;
import java.util.List;

@PageTitle("Current Courses")
@Route(value = "current-courses", layout = MainLayout.class)
public class CurrentCoursesView extends VerticalLayout {

    public CurrentCoursesView() {
        Grid<Course> courseGrid = new Grid<>(Course.class);
        courseGrid.setColumns("courseName");

        List<Course> enrolledCourses = List.of(
                new Course("MATH101", 30, 25),
                new Course("PHYS201", 40, 38)
        );

        courseGrid.setItems(enrolledCourses);
        add(courseGrid);
    }
}