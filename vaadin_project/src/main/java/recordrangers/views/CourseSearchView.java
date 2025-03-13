package recordrangers.views;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import recordrangers.models.Course;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@PageTitle("Course Search")
@Route(value = "course-search", layout = MainLayout.class)
public class CourseSearchView extends VerticalLayout {

    private Grid<Course> courseGrid = new Grid<>(Course.class);
    private TextField searchField = new TextField("Search Courses");

    // Hard-coded JDBC credentials
    private static final String DB_URL = "jdbc:mysql://localhost:3306/UniversityDB";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "Backstroke1*";

    public CourseSearchView() {
        // Configure the search field
        searchField.setPlaceholder("Enter course code...");
        searchField.setClearButtonVisible(true);
        searchField.setValueChangeMode(ValueChangeMode.LAZY);
        searchField.addValueChangeListener(e -> updateCourseGrid(e.getValue()));

        HorizontalLayout searchLayout = new HorizontalLayout(searchField);
        searchLayout.setWidth("100%");

        // Configure the grid to display only the selected properties.
        
        courseGrid.setColumns("courseId", "courseName", "courseCode", "numCredits", "termLabel", "maxCapacity", "startDate", "endDate");
        courseGrid.setSizeFull();

        // Initial fetch of courses without filter
        updateCourseGrid("");

        add(searchLayout, courseGrid);
        setSizeFull();
    }

    /**
     * Fetches courses from the database and updates the grid.
     * If a non-empty filterText is provided, only courses with a name
     * containing the text (case-insensitive) are returned.
     */
    private void updateCourseGrid(String filterText) {
        List<Course> courses = fetchCoursesFromDB(filterText);
        courseGrid.setItems(courses);
    }

    /**
     * Uses JDBC to fetch courses from the database.
     * If filterText is non-empty, it adds a WHERE clause to filter by course_name.
     */
    private List<Course> fetchCoursesFromDB(String filterText) {
        List<Course> courses = new ArrayList<>();
        String baseQuery = "SELECT course_id, course_name, course_code, num_credits, description, " +
                "capacity, start_date, end_date, term_label, days, start_time, end_time, location " +
                "FROM course";

        String whereClause = "";
        if (filterText != null && !filterText.isEmpty()) {
            whereClause = " WHERE course_code LIKE ?";
        }

        String sql = baseQuery + whereClause;

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            if (!whereClause.isEmpty()) {
                String wildcard = "%" + filterText + "%";
                pstmt.setString(1, wildcard);
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
