package recordrangers.models;
public class Student extends User{
	enum Standing {
		Good,
		Warning,
		Probation,
		Suspension
	}
	
	Standing academic_standing;
	int year_level;
	// List<Course> registered_courses; 
	// add once course class definition is merged
}
