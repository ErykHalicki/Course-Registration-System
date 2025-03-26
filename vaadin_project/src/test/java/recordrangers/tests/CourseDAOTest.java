package recordrangers.tests;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import recordrangers.models.Course;
import recordrangers.services.CourseDAO;
import recordrangers.services.DatabaseConnection;

class CourseDAOTest{
    private static Connection connection = (Connection) DatabaseConnection.getInstance();

    @BeforeAll
    static void setupDatabase() throws Exception {
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
        CourseDAO courseDAO = new CourseDAO();
        ArrayList<Course> courses = courseDAO.searchByCourseCode("CS101");
        assertFalse(courses.isEmpty(), "Should return at least one course");
        assertEquals("CS101", courses.get(0).getCourseCode());
    }

    @Test
    void testGetCourseDetails() throws Exception {
        Course course = CourseDAO.getCourseDetails(101);
        assertNotNull(course, "Course should not be null");
        assertEquals("CS101", course.getCourseCode());
        assertEquals("Intro to CS", course.getCourseCode());
    }

    @Test
    void testUpdateCourseCapacity() throws Exception {
        boolean updated = CourseDAO.updateCourseCapacity(101, 50);
        assertTrue(updated, "Capacity should be updated successfully");
    }

    @Test
    void testAddStudentToWaitlist() throws Exception {
        CourseDAO courseDAO = new CourseDAO();
        boolean added = courseDAO.addStudentToWaitlist(201, 101);
        assertTrue(added, "Student should be added to the waitlist");
    }
}