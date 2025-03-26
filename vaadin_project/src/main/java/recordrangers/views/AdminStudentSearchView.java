package recordrangers.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import recordrangers.models.Student;
import recordrangers.services.StudentSearch;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@PageTitle("Admin Student Search")
@Route(value = "admin-student-search", layout = AdminHomeView.class)
public class AdminStudentSearchView extends VerticalLayout {

    private StudentSearch studentSearch;

    // Search/filter fields
    private final TextField searchField = new TextField("Search by Name");
    private final ComboBox<String> sortByCombo = new ComboBox<>("Sort By");
    private final ComboBox<String> minGradeCombo = new ComboBox<>("Minimum Grade");
    private final ComboBox<String> maxGradeCombo = new ComboBox<>("Maximum Grade");
    private final ComboBox<String> courseNameCombo = new ComboBox<>("Course Name");

    private final Button searchButton = new Button("Search");
    private final Grid<Student> studentGrid = new Grid<>(Student.class);

    public AdminStudentSearchView() {
        // Page title
        H2 title = new H2("Admin Student Search");
        add(title);

        // Initialize the StudentSearch service
        try {
            studentSearch = new StudentSearch();
        } catch (SQLException ex) {
            ex.printStackTrace();
            add("Error initializing Student Search Service.");
            return;
        }

        // Populate sortByCombo with the columns you want to sort on
        sortByCombo.setItems("first_name", "studentId", "enrollment_date");
        sortByCombo.setValue("first_name");

        // Populate minGradeCombo and maxGradeCombo
        List<String> grades = List.of("A", "B", "C", "D", "F");
        minGradeCombo.setItems(grades);
        minGradeCombo.setPlaceholder("Select minimum grade");
        maxGradeCombo.setItems(grades);
        maxGradeCombo.setPlaceholder("Select maximum grade");

        // Populate courseNameCombo (replace with dynamic list if needed)
        courseNameCombo.setItems("MATH", "COSC", "ENG", "ECON");
        courseNameCombo.setPlaceholder("Select course");

        // Create a vertical layout to stack each filter
        VerticalLayout filtersLayout = new VerticalLayout(
                searchField,
                sortByCombo,
                minGradeCombo,
                maxGradeCombo,
                courseNameCombo,
                searchButton
        );
        filtersLayout.setSpacing(true);
        filtersLayout.setPadding(false);

        // Configure the student grid to display only the required columns
        studentGrid.setColumns("studentId", "firstName", "lastName", "email", "enrollment_date", "status");

        // Add the filter layout and the grid to the main layout
        add(filtersLayout, studentGrid);

        // Set the search button click listener
        searchButton.addClickListener(e -> updateGrid());

        // Optionally load initial data
        updateGrid();
    }

    /**
     * Gathers filter values and updates the grid with the search results.
     */
    private void updateGrid() {
        String searchTerm = searchField.getValue().trim();
        String sortBy = sortByCombo.getValue();
        String minGrade = minGradeCombo.getValue() != null ? minGradeCombo.getValue() : "";
        String maxGrade = maxGradeCombo.getValue() != null ? maxGradeCombo.getValue() : "";
        String courseName = courseNameCombo.getValue() != null ? courseNameCombo.getValue() : "";

        try {
            ArrayList<Student> students = studentSearch.searchStudents(searchTerm, sortBy, minGrade, maxGrade, courseName);
            studentGrid.setItems(students);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
