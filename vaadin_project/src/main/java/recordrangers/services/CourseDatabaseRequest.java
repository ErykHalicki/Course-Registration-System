package recordrangers.services;

import recordrangers.models.Course;
import java.util.List;

public class CourseDatabaseRequest {
	
	//TODO add real database requesting functionality here
	public static List<Course> getAllCourses(){
		//dummy data
		List<Course> allCourses = List.of(
	            new Course("MATH101", 30, 25),
	            new Course("MATH201", 30, 25),
	            new Course("MATH211", 30, 25),
	            new Course("PHYS201", 40, 38),
	            new Course("PHYS211", 40, 38),
	            new Course("COSC305", 50, 45),
	            new Course("ENG202", 25, 20),
	            new Course("ENG312", 35, 20),
	            new Course("BIO150", 10, 30),
	            new Course("BIO151", 10, 30)
	        );
		return  allCourses;
	}
	
}
