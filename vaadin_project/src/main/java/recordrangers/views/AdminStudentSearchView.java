package recordrangers.views;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.stream.Collectors;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import recordrangers.models.Student;
import recordrangers.services.StudentDAO;

@PageTitle("Admin Student Search")
@Route(value = "admin-search", layout = AdminHomeView.class)
public class AdminStudentSearchView extends VerticalLayout {
    StudentDAO databaseInterface = new StudentDAO();

    private final TextField searchField = new TextField("Search by Name...");
    private final Button searchButton = new Button("Search");
    private final Grid<Student> studentGrid = new Grid<>(Student.class);

    public AdminStudentSearchView() throws SQLException {
        // Page title
        H2 title = new H2("Admin Student Search");

        // Configure grid columns
        Grid<Student> studentGrid = new Grid<>(Student.class);
        studentGrid.setColumns("studentId", "firstName", "lastName", "email", "enrollment_date", "status");
       
        ArrayList<Student> students;
        try {
            students = databaseInterface.getAllStudents();
            studentGrid.setItems(students);
            add(studentGrid);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // Search button listener to update the grid based on search term
        searchButton.addClickListener(e -> updateGrid());

        // Arrange components vertically
        add(title, searchField, searchButton, studentGrid);
        setPadding(true);
        setSpacing(true);

        // Initial load of student data
        updateGrid();
    }
    /**
     * Updates the grid by fetching the student list and filtering it based on the search term.
     */
    private void updateGrid() {
        try {
            ArrayList<Student> students = databaseInterface.getAllStudents();
            String searchTerm = searchField.getValue().toLowerCase().trim();
            if (!searchTerm.isEmpty()) {
                students = (ArrayList<Student>) students.stream()
                        .filter(s -> s.getFirstName().toLowerCase().contains(searchTerm))
                        .collect(Collectors.toList());
            }
            studentGrid.setItems(students);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
