package recordrangers.tests;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import recordrangers.models.Course;

import java.util.List;
import java.util.stream.Collectors;

/*
public class CourseSearchViewTest {

    @Test
    public void testGridIsPopulatedWithDatabaseData() {
        // Create the view (which opens the JDBC connection internally)
        CourseSearchView view = new CourseSearchView();

        // Force the Grid to fetch and display all courses (no filter)
        view.updateCourseGrid("");

        // Retrieve items from the Grid
        List<Course> gridCourses = view.getCourseGrid()
                                       .getGenericDataView()
                                       .getItems()
                                       .collect(Collectors.toList());

        // 1) Check we have at least 9 courses
        Assertions.assertTrue(
            gridCourses.size() >= 9,
            "Expected at least 9 courses in the Grid from the DB"
        );

        // 2) Check that "Intro to Computer Science" with code "CSC 101" is present
        boolean foundIntroCS = gridCourses.stream()
            .anyMatch(c -> "Intro to Computer Science".equals(c.getCourseName()) 
                        && "COSC 101".equals(c.getCourseCode()));

        Assertions.assertTrue(
            foundIntroCS,
            "Expected to find 'Intro to Computer Science' (COSC 101) in the Grid data"
        );
    }
}
*/
