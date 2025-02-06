import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class SearcherTest {
	@Test
	void Course_search_returns_all_mathching() {
		ArrayList<Course> courses = new ArrayList<Course>();
		courses.add(new Course("COSC310"));
		courses.add(new Course("COSC222"));
		courses.add(new Course("COSC211"));
		courses.add(new Course("COSC499"));
		courses.add(new Course("MATH221"));
		courses.add(new Course("MATH101"));
		courses.add(new Course("C_SC"));
		courses.add(new Course("CSC121"));
		
		ArrayList<Course> manually_filtered_courses = new ArrayList<Course>();
		manually_filtered_courses.add(new Course("COSC499"));
		manually_filtered_courses.add(new Course("COSC310"));
		manually_filtered_courses.add(new Course("COSC222"));
		manually_filtered_courses.add(new Course("COSC211"));
		
		ArrayList<Course> filtered_courses = new ArrayList<Course>(Searcher.searchCourseByName("COSC", courses));
				
		assertTrue(filtered_courses.size() == manually_filtered_courses.size()); // same size
		assertTrue(filtered_courses.containsAll(manually_filtered_courses)); // a contains b
		assertTrue(manually_filtered_courses.containsAll(filtered_courses)); // b contains a
	}

}
