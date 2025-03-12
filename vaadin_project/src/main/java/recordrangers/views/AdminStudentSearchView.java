package recordrangers.views;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Admin Student Search")
@Route(value = "admin-search", layout = AdminHomeView.class)
public class AdminStudentSearchView extends VerticalLayout {

    
    private static final String DB_URL = "jdbc:mysql://localhost:3306/UniversityDB";// update these Log in details as needed.
    private static final String DB_USER = "your_username";
    private static final String DB_PASSWORD = "your_password";

    private final TextField searchField = new TextField("Search by Name");
    private final Button searchButton = new Button("Search");
    private final Grid<StudentDetails> studentGrid = new Grid<>(StudentDetails.class);

    public AdminStudentSearchView() {
        // Page title
        H2 title = new H2("Admin Student Search");

        // Configure grid columns
        configureGrid();

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
     * Configures the grid to show the desired columns.
     */
    private void configureGrid() {
        // Remove any auto-generated columns
        studentGrid.removeAllColumns();
        studentGrid.addColumn(StudentDetails::getStudentId).setHeader("Student ID");
        studentGrid.addColumn(StudentDetails::getFullName).setHeader("Full Name");
        studentGrid.addColumn(StudentDetails::getEmail).setHeader("Email");
        studentGrid.addColumn(sd -> sd.getEnrollmentDate() != null ? sd.getEnrollmentDate().toString() : "")
                   .setHeader("Enrollment Date");
        studentGrid.addColumn(StudentDetails::getStatus).setHeader("Status");
        studentGrid.setSizeFull();
    }

    /**
     * Updates the grid by fetching the student list and filtering it based on the search term.
     */
    private void updateGrid() {
        List<StudentDetails> students = getStudentList();
        String searchTerm = searchField.getValue().toLowerCase().trim();
        if (!searchTerm.isEmpty()) {
            students = students.stream()
                    .filter(s -> s.getFullName().toLowerCase().contains(searchTerm))
                    .collect(Collectors.toList());
        }
        studentGrid.setItems(students);
    }

    /**
     * Retrieves a list of students by joining the User and Student tables.
     */
    @SuppressWarnings("CallToPrintStackTrace")
    private List<StudentDetails> getStudentList() {
        List<StudentDetails> list = new ArrayList<>();
        String sql = "SELECT u.user_id, CONCAT(u.first_name, ' ', u.last_name) AS fullName, u.email, " +
                     "s.enrollment_date, s.status " +
                     "FROM User u " +
                     "JOIN Student s ON u.user_id = s.student_id";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int studentId = rs.getInt("user_id");
                String fullName = rs.getString("fullName");
                String email = rs.getString("email");
                Date enrollmentDate = rs.getDate("enrollment_date");
                String status = rs.getString("status");
                list.add(new StudentDetails(studentId, fullName, email, enrollmentDate, status));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    /**
     * A simple POJO to hold student details for display.
     */
    public static class StudentDetails {
        private int studentId;
        private String fullName;
        private String email;
        private Date enrollmentDate;
        private String status;

        public StudentDetails(int studentId, String fullName, String email, Date enrollmentDate, String status) {
            this.studentId = studentId;
            this.fullName = fullName;
            this.email = email;
            this.enrollmentDate = enrollmentDate;
            this.status = status;
        }

        public int getStudentId() {
            return studentId;
        }

        public void setStudentId(int studentId) {
            this.studentId = studentId;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public Date getEnrollmentDate() {
            return enrollmentDate;
        }

        public void setEnrollmentDate(Date enrollmentDate) {
            this.enrollmentDate = enrollmentDate;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
