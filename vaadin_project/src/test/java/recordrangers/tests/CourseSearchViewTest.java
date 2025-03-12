package recordrangers.tests;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import recordrangers.models.Course;
import recordrangers.views.CourseSearchView;

import java.sql.SQLException;
import java.util.List;

public class CourseSearchViewTest {

    private CourseSearchView courseSearchView;
    private TextField searchField;
    private Grid<Course> courseGrid;

    @BeforeEach
    void setUp() throws SQLException{
        courseSearchView = new CourseSearchView();
        searchField = courseSearchView.getSearchField(); // Add a getter in your CourseSearchView for searchField
        courseGrid = courseSearchView.getCourseGrid();
    }

    @Test
    void testComponentsInitialized() {
        assertNotNull(searchField, "Search field should be initialized");
        assertNotNull(courseGrid, "Grid should be initialized");
        assertEquals(ValueChangeMode.EAGER, searchField.getValueChangeMode(), "Search should update eagerly");
    }

    @Test
    void testInitialCourseListNotEmpty() {
        List<Course> initialCourses = courseGrid.getListDataView().getItems().toList();
        assertFalse(initialCourses.isEmpty(), "Initial course list should not be empty");
    }

    @Test
    void testSearchFiltersCourses() {
        // Simulate user typing "MATH"
        searchField.setValue("MATH");
        
        List<Course> filteredCourses = courseGrid.getListDataView().getItems().toList();
        
        for(Course c : filteredCourses) {
        	assertTrue(c.getCourseCode().toLowerCase().contains("math"), "All courses should match 'MATH'");
        }
    }

    @Test
    void testEmptySearchShowsAllCourses() {
        // Now clear search
        searchField.setValue("");
        List<Course> allCourses = courseGrid.getListDataView().getItems().toList();
        assertTrue(allCourses.size() >= 3, "All courses should be shown when search is empty");
    }
}