package recordrangers.views;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import recordrangers.models.Course;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CourseRegistrationViewTest {

    private CourseRegistrationView view;

    @BeforeEach
    void setUp() {
        // Initialize the view, which also initializes the courses list
        view = new CourseRegistrationView();
    }

    @Test
    void testFilterByLevel100() {
        // Filter by "100" level, no subject, no credits
        List<Course> filtered = view.filterCourses("100", "", "");
        // We expect to match "MATH101" from the example data
        assertEquals(1, filtered.size());
        assertEquals("MATH101", filtered.get(0).getCourseName());
    }

    @Test
    void testFilterBySubject() {
        // Filter by "PHYS", no level, no credits
        List<Course> filtered = view.filterCourses("", "PHYS", "");
        // Expect to match "PHYS201"
        assertEquals(1, filtered.size());
        assertEquals("PHYS201", filtered.get(0).getCourseName());
    }

    @Test
    void testFilterByLevelAndSubject() {
        // Filter by level "200" and subject "PHYS", no credits
        List<Course> filtered = view.filterCourses("200", "PHYS", "");
        // Expect 1 match: "PHYS201"
        assertEquals(1, filtered.size());
        assertEquals("PHYS201", filtered.get(0).getCourseName());
    }

    @Test
    void testFilterNoCriteria() {
        // No filters
        List<Course> filtered = view.filterCourses("", "", "");
        // Should return all courses
        assertEquals(3, filtered.size()); // we had 3 in the example
    }
}
