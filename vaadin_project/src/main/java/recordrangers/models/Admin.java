package recordrangers.models;
public class Admin extends User{
	enum Level {
		Super,
		Course
	}
	//Super admin: can assign and remove course admins, as well as students in all courses
	//Course admin: can assign and remove students within their courses
	//enum allows for scaling to more types of admins (department head, TA, etc.)
	
	Level admin_level;
	// List<Course> assigned_courses; 
	// Super admins will have this field blank
	// add once course class definition is merged
}
