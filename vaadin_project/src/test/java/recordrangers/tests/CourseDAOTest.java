package recordrangers.tests;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import recordrangers.models.Course;
import recordrangers.services.CourseDAO;
import recordrangers.services.DatabaseConnection;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)  // Ensure tests run in order
public class CourseDAOTest {
    
    private static Connection connection;

    @BeforeEach
    void setUp() throws Exception {
        // Initialize connection if needed
    }
    @Test
    @Order(1)
    void testInsertCourse() throws SQLException {
    	CourseDAO.addCourse(new Course(
    		    "CS101", 
    		    "Introduction to Computer Science", 
    		    3, 
    		    "Fall 2025", 
    		    LocalDate.parse("2025-09-01"), 
    		    LocalDate.parse("2025-12-15")
    		));
    }
    @Test
    @Order(2)
    void testSearchByCourseCode() throws SQLException {
        ArrayList<Course> courses = CourseDAO.searchByCourseCode("CS101");
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
}