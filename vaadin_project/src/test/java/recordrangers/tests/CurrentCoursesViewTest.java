package recordrangers.tests;
/*
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.server.VaadinSession;

import org.junit.jupiter.api.Test;
import recordrangers.models.Course;
import recordrangers.models.User;
import recordrangers.services.Auth;
import recordrangers.services.CourseRegistration;
import recordrangers.views.CurrentCoursesView;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.List;

public class CurrentCoursesViewTest {

    @Test
    void testGridDisplaysCourses() throws IllegalAccessException, SQLException {
        // GIVEN: A new instance of the CurrentCoursesView
    	User user = Auth.signIn("john.doe@example.com", "password123");
       
        VaadinSession.getCurrent().setAttribute("loggedInUser", user);
        CourseRegistration.registerStudent(user.getUserId(), 1);
        CourseRegistration.registerStudent(user.getUserId(), 2);
        	
        CurrentCoursesView view = new CurrentCoursesView();

        // WHEN: We attempt to access the Grid via reflection
        Grid<Course> courseGrid = getCourseGrid(view);
        assertNotNull(courseGrid, "The Grid should not be null.");

        // THEN: Verify that the Grid has 2 items (MATH101 and PHYS201)
        List<Course> items = courseGrid.getGenericDataView().getItems().toList();
        assertEquals(2, items.size(), "The Grid should display 2 courses.");

        // Check that the correct courses are present
        assertEquals(1, items.get(0).getCourseId());
        assertEquals(2, items.get(1).getCourseId());
    }


    @SuppressWarnings("unchecked")
    private Grid<Course> getCourseGrid(CurrentCoursesView view) throws IllegalAccessException {
        for (Field field : CurrentCoursesView.class.getDeclaredFields()) {
            if (Grid.class.equals(field.getType())) {
                field.setAccessible(true);
                return (Grid<Course>) field.get(view);
            }
        }
        return null;
    }
}
*/
