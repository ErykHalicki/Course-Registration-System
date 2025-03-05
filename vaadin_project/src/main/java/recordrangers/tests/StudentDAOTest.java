package recordrangers.tests;
/*import org.junit.jupiter.api.*;
import java.sql.*;
import static org.junit.jupiter.api.Assertions.*;

import recordrangers.services.*;

class StudentDAOTest {
    private Connection connection;
    
    @BeforeEach
    void setUp() throws SQLException {
        connection = DatabaseConnection.getConnection();
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("CREATE TABLE courses (course_id INT PRIMARY KEY, max_capacity INT)");
            stmt.execute("CREATE TABLE enrollments (student_id INT, course_id INT, PRIMARY KEY (student_id, course_id))");
            stmt.execute("CREATE TABLE waitlists (student_id INT, course_id INT, PRIMARY KEY (student_id, course_id))");
            stmt.execute("INSERT INTO courses VALUES (101, 30)");
        }
    }

    @AfterEach
    void tearDown() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("DROP TABLE enrollments");
            stmt.execute("DROP TABLE waitlists");
            stmt.execute("DROP TABLE courses");
        }
        connection.close();
    }

    @Test
    void testEnrollStudent_AlreadyEnrolled() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("INSERT INTO enrollments VALUES (1, 101)");
        }
        String result = StudentDAO.enrollStudent(1, 101);
        assertEquals("Student is already enrolled in this course.", result);
    }

    @Test
    void testEnrollStudent_SuccessfulEnrollment() throws SQLException {
        String result = StudentDAO.enrollStudent(2, 101);
        assertEquals("Student successfully enrolled.", result);
    }

    @Test
    void testEnrollStudent_CourseFull_AddedToWaitlist() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            for (int i = 1; i <= 30; i++) {
                stmt.execute("INSERT INTO enrollments VALUES (" + i + ", 101)");
            }
        }
        String result = StudentDAO.enrollStudent(31, 101);
        assertEquals("Course is full. Student added to waitlist.", result);
    }

    @Test
    void testEnrollStudent_DatabaseError() {
        assertThrows(Exception.class, () -> StudentDAO.enrollStudent(1, 999));
    }
}*/

