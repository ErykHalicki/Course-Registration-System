package recordrangers.views;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.stream.Collectors;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
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

         // Initial load of student data
         updateGrid();

        // Enable real-time search as the user types
        searchField.setValueChangeMode(ValueChangeMode.EAGER);
        searchField.addValueChangeListener(event -> updateGrid());

        // Arrange components vertically
        add(title, searchField, searchButton, studentGrid);
        setPadding(true);
        setSpacing(true);
    }
    /**
     * Updates the grid by fetching the student list and filtering it based on the search term.
     */
    private void updateGrid() {
        try {
            ArrayList<Student> students = databaseInterface.getAllStudents();
            String searchTerm = searchField.getValue().toLowerCase().trim();
            if (!searchTerm.isEmpty()) {
                // First, get names that start with the search term
                ArrayList<Student> startsWith = (ArrayList<Student>) students.stream()
                        .filter(s -> s.getFirstName().toLowerCase().startsWith(searchTerm))
                        .collect(Collectors.toList());
    
                // Then, get names that contain the search term but do not start with it
                ArrayList<Student> contains = (ArrayList<Student>) students.stream()
                        .filter(s -> s.getFirstName().toLowerCase().contains(searchTerm) && 
                                     !s.getFirstName().toLowerCase().startsWith(searchTerm))
                        .collect(Collectors.toList());
    
                // Combine results, with "startsWith" appearing first
                startsWith.addAll(contains);
                students = startsWith;
            }
            studentGrid.setItems(students);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

