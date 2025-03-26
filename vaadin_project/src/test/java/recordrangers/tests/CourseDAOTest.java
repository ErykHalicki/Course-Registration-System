package recordrangers.tests;

import org.junit.jupiter.api.*;
import recordrangers.models.Course;
import recordrangers.services.CourseDAO;
import recordrangers.services.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)  // Ensure tests run in order
public class CourseDAOTest {
    private static CourseDAO courseDAO;
    private static Connection connection;

    @BeforeAll
    static void setUp() throws SQLException {
        courseDAO = new CourseDAO();
        connection = DatabaseConnection.getInstance().getConnection();
        assertNotNull(connection, "Database connection should not be null");
    }

    @Test
    @Order(1)
    void testInsertCourse() throws SQLException {
        String sql = "INSERT INTO Course (course_code, course_name, capacity, term_label, start_date, end_date) VALUES ('CS101', 'Intro to CS', 50, 'Fall 2025', '2025-09-01', '2025-12-15')";
        connection.createStatement().executeUpdate(sql);
    }

    @Test
    @Order(2)
    void testSearchByCourseCode() throws SQLException {
        ArrayList<Course> courses = courseDAO.searchByCourseCode("CS101");
        assertFalse(courses.isEmpty(), "Course should exist in the database");
        assertEquals("CS101", courses.get(0).getCourseCode());
    }

    @Test
    @Order(3)
    void testGetAllCourses() throws SQLException {
        List<Course> courses = CourseDAO.getAllCourses();
        assertFalse(courses.isEmpty(), "Courses should be retrieved from database");
    }

    @Test
    @Order(4)
    void testGetCourseDetails() {
        Course course = CourseDAO.getCourseDetails(1);
        assertNotNull(course, "Course should exist with ID 1");
        assertEquals(1, course.getCourseId());
    }

    @Test
    @Order(5)
    void testUpdateCourseCapacity() {
        boolean updated = CourseDAO.updateCourseCapacity(1, 100);
        assertTrue(updated, "Course capacity should be updated");

        Course updatedCourse = CourseDAO.getCourseDetails(1);
        assertNotNull(updatedCourse);
        assertEquals(100, updatedCourse.getMaxCapacity(), "Updated capacity should be 100");
    }


    @Test
    @Order(6)
    void testCourseCapacity() throws SQLException {
        int enrolled = CourseDAO.courseCapacity(1);
        assertTrue(enrolled >= 0, "Enrollment count should be non-negative");
    }

    @AfterAll
    static void cleanUp() throws SQLException {
        connection.createStatement().executeUpdate("DELETE FROM Course WHERE course_code = 'CS101'");
        connection.close();
    }
}