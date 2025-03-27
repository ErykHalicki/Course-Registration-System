package recordrangers.tests;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import recordrangers.models.Course;
import recordrangers.services.CourseDAO;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)  // Ensure tests run in order
public class CourseDAOTest {
    @Mock
    private static Connection connection;
        
        @Mock
        private PreparedStatement pstmt;
        
        @Mock
        private ResultSet rst;
        
        @InjectMocks
        private CourseDAO courseDAO;
    
        @BeforeEach
        void setUp() throws Exception {
            MockitoAnnotations.openMocks(this);
            when(connection.prepareStatement(anyString())).thenReturn(pstmt);
            when(pstmt.executeQuery()).thenReturn(rst);
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
        @Test
        void testGetEnrolledCourses() throws Exception {
            when(rst.next()).thenReturn(true, false);
            when(rst.getString("course_name")).thenReturn("Algorithms");
            when(rst.getString("course_code")).thenReturn("CS101");
            when(rst.getInt("num_credits")).thenReturn(3);
            when(rst.getString("term_label")).thenReturn("Fall 2024");
            when(rst.getString("start_date")).thenReturn("2024-09-01");
            when(rst.getString("end_date")).thenReturn("2024-12-15");
            
            ArrayList<Course> courses = CourseDAO.getEnrolledCourses(1);
            assertEquals(1, courses.size());
            assertEquals("Algorithms", courses.get(0).getCourseName());
        }
        
        @Test
        void testAddCourse() throws Exception {
            when(pstmt.executeUpdate()).thenReturn(1);
            Course course = new Course("Algorithms", "CS101", 3, "Fall 2024", LocalDate.parse("2024-09-01"), LocalDate.parse("2024-12-15"));
            
            boolean result = CourseDAO.addCourse(course);
            assertTrue(result);
        }
        
        @Test
        void testUpdateCourse() throws Exception {
            when(pstmt.executeUpdate()).thenReturn(1);
            Course course = new Course("Algorithms", "CS101", 3, "Fall 2024", LocalDate.parse("2024-09-01"), LocalDate.parse("2024-12-15"));
            course.setCourseId(1);
            
            boolean result = CourseDAO.updateCourse(course);
            assertTrue(result);
        }
        
        @Test
        void testDeleteCourse() throws Exception {
            when(pstmt.executeUpdate()).thenReturn(1);
            
            boolean result = CourseDAO.deleteCourse("CS101");
            assertTrue(result);
        }
    
        @AfterAll
        static void cleanUp() throws SQLException {
            try {
                connection.createStatement().executeUpdate("DELETE FROM Course WHERE course_code = 'CS101'");
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            connection.close();
    }
}