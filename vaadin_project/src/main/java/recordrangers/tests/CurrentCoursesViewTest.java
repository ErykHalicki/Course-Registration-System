package recordrangers.views;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.vaadin.flow.component.grid.Grid;
import org.junit.jupiter.api.Test;
import recordrangers.models.Course;

import java.lang.reflect.Field;
import java.util.List;

public class CurrentCoursesViewTest {

    @Test
    void testGridDisplaysCourses() throws IllegalAccessException {
        // GIVEN: A new instance of the CurrentCoursesView
        CurrentCoursesView view = new CurrentCoursesView();

        // WHEN: We attempt to access the Grid via reflection
        Grid<Course> courseGrid = getCourseGrid(view);
        assertNotNull(courseGrid, "The Grid should not be null.");

        // THEN: Verify that the Grid has 2 items (MATH101 and PHYS201)
        List<Course> items = courseGrid.getGenericDataView().getItems().toList();
        assertEquals(2, items.size(), "The Grid should display 2 courses.");

        // Check that the correct courses are present
        assertEquals("MATH101", items.get(0).getCourseName());
        assertEquals("PHYS201", items.get(1).getCourseName());
    }

    /**
     * Uses reflection to find the first Grid<Course> field in the CurrentCoursesView.
     */
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
