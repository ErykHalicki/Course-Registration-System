package recordrangers.views;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import recordrangers.models.Course;
import java.util.List;

@PageTitle("Course Registration")
@Route(value = "register", layout = MainLayout.class)
public class CourseRegistrationView extends VerticalLayout {

    public CourseRegistrationView() {
        Grid<Course> courseGrid = new Grid<>(Course.class);
        courseGrid.setColumns("courseName", "maxCapacity", "enrollment");

        List<Course> courses = List.of(
                new Course("MATH101", 30, 25),
                new Course("PHYS201", 40, 38),
                new Course("COSC305", 50, 45)
        );

        courseGrid.setItems(courses);
        add(courseGrid);
    }
}