package recordrangers.tests;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import recordrangers.models.Course;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CurrentCoursesViewTest {

    private CurrentCoursesView currentCoursesView;

    @BeforeEach
    void setUp() {
        // Initialize a fresh view before each test
        currentCoursesView = new CurrentCoursesView();
    }

    @Test
    void testInitialCourses() {
        // Check that our dummy data (3 courses) is loaded
        List<Course> courses = currentCoursesView.getEnrolledCourses();
        assertEquals(3, courses.size(), 
            "There should be exactly 3 dummy courses initially.");
    }

    @Test
    void testDropCourse() {
        // Drop the first course and ensure it's removed
        List<Course> courses = currentCoursesView.getEnrolledCourses();
        assertFalse(courses.isEmpty(), 
            "The course list shouldn't be empty initially.");

        Course firstCourse = courses.get(0);
        currentCoursesView.dropCourse(firstCourse);

        assertFalse(courses.contains(firstCourse),
            "After dropping the first course, it should no longer be in the list.");
    }
}
