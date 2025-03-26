package recordrangers.tests;

import static org.junit.Assert.*;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.textfield.TextField;
import recordrangers.models.Student;
import recordrangers.views.AdminStudentSearchView;

public class AdminStudentSearchViewTest {

    private AdminStudentSearchView view;

    @Before
    public void setUp() {
        // Create a new UI instance for Vaadin (required for UI components)
        UI ui = new UI();
        UI.setCurrent(ui);

        // Instantiate the actual AdminStudentSearch view (which uses the real database)
        view = new AdminStudentSearchView();
    }

    @Test
    public void testGridColumnsAreDisplayed() {
        Grid<Student> grid = findGrid(view);
        assertNotNull("Grid should not be null", grid);

        // Since we configured the grid with 6 columns
        int expectedColumnCount = 6;
        int actualColumnCount = grid.getColumns().size();
        assertEquals("Grid should have " + expectedColumnCount + " columns", expectedColumnCount, actualColumnCount);
    }

    @Test
    public void testSearchPopulatesGrid() {
        // Set the search field to an empty string to load all students
        TextField searchField = findSearchField(view);
        assertNotNull("Search field should not be null", searchField);
        searchField.setValue("");  // empty search should return all students

        // Find and click the search button
        Button searchButton = findSearchButton(view);
        assertNotNull("Search button should not be null", searchButton);
        searchButton.click();

        // Get the grid and verify that it has student records
        Grid<Student> grid = findGrid(view);
        List<Student> students = grid.getGenericDataView().getItems().collect(Collectors.toList());
        assertTrue("Grid should have at least one student", students.size() > 0);
    }

    // Helper method to find the Grid component within the view
    private Grid<Student> findGrid(AdminStudentSearchView view) {
        return view.getChildren()
                .filter(component -> component instanceof Grid)
                .map(component -> (Grid<Student>) component)
                .findFirst()
                .orElse(null);
    }

    // Helper method to find the search field (TextField) within the view
    private TextField findSearchField(AdminStudentSearchView view) {
        return view.getChildren()
                .filter(component -> component instanceof TextField)
                .map(component -> (TextField) component)
                .findFirst()
                .orElse(null);
    }

    // Helper method to find the search button within the view
    private Button findSearchButton(AdminStudentSearchView view) {
        return view.getChildren()
                .filter(component -> component instanceof Button)
                .map(component -> (Button) component)
                .filter(button -> "Search".equals(button.getText()))
                .findFirst()
                .orElse(null);
    }
}
