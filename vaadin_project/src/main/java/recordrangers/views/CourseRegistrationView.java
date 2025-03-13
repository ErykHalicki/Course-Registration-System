package recordrangers.views;

import java.sql.SQLException;
import java.util.ArrayList;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import recordrangers.models.Course;
import recordrangers.services.CourseDAO;

@PageTitle("Course Registration")
@Route(value = "register", layout = StudentHomeView.class)
public class CourseRegistrationView extends VerticalLayout {

    @SuppressWarnings("CallToPrintStackTrace")
    public CourseRegistrationView() throws SQLException {
        Grid<Course> courseGrid = new Grid<>(Course.class);
        courseGrid.setColumns("courseCode", "courseName", "maxCapacity", "schedule");

        ArrayList<Course> courses;
        try {
            CourseDAO databaseInterface = new CourseDAO();
            courses = databaseInterface.getAllCourses();
            courseGrid.setItems(courses);
            add(courseGrid);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        
    }
}