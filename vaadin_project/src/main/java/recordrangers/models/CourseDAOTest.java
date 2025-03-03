import org.junit.jupiter.api.*;
import java.sql.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class CourseDAOTest {
    private static Connection connection;
    private static CourseDAO courseDAO;

    @BeforeAll
    static void setupDatabase() throws Exception {
        connection = DatabaseConnection.getConnection();
        courseDAO = new CourseDAO(connection);
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("CREATE TABLE courses (course_id INT PRIMARY KEY, course_code VARCHAR(10), course_name VARCHAR(100), max_capacity INT, schedule VARCHAR(50))");
            stmt.execute("CREATE TABLE waitlists (student_id INT, course_id INT, request_date TIMESTAMP)");
            stmt.execute("INSERT INTO courses VALUES (101, 'CS101', 'Intro to CS', 30, 'MWF 10-11 AM')");
        }
    }

    @AfterAll
    static void tearDownDatabase() throws Exception {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("DROP TABLE waitlists");
            stmt.execute("DROP TABLE courses");
        }
        connection.close();
    }

    @Test
    void testSearchByCourseName() throws Exception {
        ArrayList<Course> courses = courseDAO.searchByCourseName("CS101");
        assertFalse(courses.isEmpty(), "Should return at least one course");
        assertEquals("CS101", courses.get(0).getCourseCode());
    }

    @Test
    void testGetCourseDetails() throws Exception {
        Course course = CourseDAO.getCourseDetails(101);
        assertNotNull(course, "Course should not be null");
        assertEquals("CS101", course.getCourseCode());
        assertEquals("Intro to CS", course.getCourseName());
    }

    @Test
    void testUpdateCourseCapacity() throws Exception {
        boolean updated = CourseDAO.updateCourseCapacity(101, 50);
        assertTrue(updated, "Capacity should be updated successfully");
    }

    @Test
    void testAddStudentToWaitlist() throws Exception {
        boolean added = courseDAO.addStudentToWaitlist(201, 101);
        assertTrue(added, "Student should be added to the waitlist");
    }
}