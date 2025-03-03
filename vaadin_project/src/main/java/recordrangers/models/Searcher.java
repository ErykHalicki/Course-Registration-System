package recordrangers.models;
import java.util.List;
import java.util.ArrayList;

public class Searcher {
	
	//method for filtering courses by name
	static public List<Course> searchCourseByName(String name, List<Course> courses){
		List<Course> result = new ArrayList<Course>();
		for(Course c: courses) {
			if(c.courseName.contains(name)) {
				result.add(c);
			}
		}
		return result;
	}
}
