package recordrangers.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import recordrangers.models.Course;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@PageTitle("Course Registration")
@Route(value = "register", layout = StudentHomeView.class)
public class CourseRegistrationView extends VerticalLayout {

    private final Grid<Course> courseGrid = new Grid<>(Course.class);
    private final TextField searchField = new TextField("Search Courses");
    private final Button registerButton = new Button("Register");

    private static final String DB_URL = "jdbc:mysql://localhost:3306/UniversityDB";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "Backstroke1*";

    public CourseRegistrationView() {
        // Title
        H1 title = new H1("Course Registration");

        // Configure the grid
        courseGrid.setColumns("courseId", "courseName", "courseCode", "numCredits", "termLabel", "startDate", "endDate");
        courseGrid.setSizeFull();

        // Initial fetch of all courses
        updateCourseGrid(null);

        // Configure the search field
        searchField.setPlaceholder("Search by name, code, or description...");
        searchField.addValueChangeListener(e -> updateCourseGrid(searchField.getValue()));

        // Register button logic
        registerButton.addClickListener(e -> {
            Course selected = courseGrid.asSingleSelect().getValue();
            if (selected == null) {
                Notification.show("Please select a course first.");
            } else {
                // Here you'd normally insert a record in a "registration" table
                // with the user info and the course ID.
                Notification.show("Registered for: " + selected.getCourseName());
            }
        });

        HorizontalLayout actionsLayout = new HorizontalLayout(searchField, registerButton);
        add(title, actionsLayout, courseGrid);
        setSizeFull();
    }

    /**
     * Fetch courses from the database (with optional search filter),
     * then update the grid items.
     */
    private void updateCourseGrid(String filterText) {
        List<Course> courses = fetchCoursesFromDB(filterText);
        courseGrid.setItems(courses);
    }

    /**
     * A simple JDBC-based method to fetch courses from the database.
     * If filterText is provided, it filters on courseName, courseCode, or description.
     */
    private List<Course> fetchCoursesFromDB(String filterText) {
        List<Course> courses = new ArrayList<>();
        String baseQuery = "SELECT course_id, course_name, course_code, num_credits, description, " +
                           "capacity, start_date, end_date, term_label, days, start_time, end_time, location " +
                           "FROM course";

        String whereClause = "";
        if (filterText != null && !filterText.isBlank()) {
            // Basic SQL injection protection via parameter binding.
            // We'll search across course_name, course_code, or description. 
            whereClause = " WHERE (course_name LIKE ? OR course_code LIKE ? OR description LIKE ?)";
        }

        String sql = baseQuery + whereClause;

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            if (!whereClause.isEmpty()) {
                String wildcard = "%" + filterText + "%";
                pstmt.setString(1, wildcard);
                pstmt.setString(2, wildcard);
                pstmt.setString(3, wildcard);
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Course course = new Course();
                    course.setCourseId(rs.getInt("course_id"));
                    course.setCourseName(rs.getString("course_name"));
                    course.setCourseCode(rs.getString("course_code"));
                    course.setNumCredits(rs.getInt("num_credits"));
                    course.setDescription(rs.getString("description"));
                    course.setMaxCapacity(rs.getInt("capacity"));

                    
                    Date sqlStartDate = rs.getDate("start_date");
                    Date sqlEndDate   = rs.getDate("end_date");
                    if (sqlStartDate != null) {
                        course.setStartDate(sqlStartDate.toLocalDate());
                    }
                    if (sqlEndDate != null) {
                        course.setEndDate(sqlEndDate.toLocalDate());
                    }

                    course.setTermLabel(rs.getString("term_label"));
                    course.setDays(rs.getString("days"));
                    
                    // For time columns:
                    Time startTime = rs.getTime("start_time");
                    Time endTime   = rs.getTime("end_time");
                    if (startTime != null) {
                        course.setStartTime(startTime.toLocalTime());
                    }
                    if (endTime != null) {
                        course.setEndTime(endTime.toLocalTime());
                    }

                    course.setLocation(rs.getString("location"));

                    courses.add(course);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Notification.show("Error fetching courses: " + e.getMessage());
        }
        return courses;
    }
}
