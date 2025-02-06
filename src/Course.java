public class Course {
	String name;
	public Course(String n) {
		name = n;
	}
	public boolean equals(Object o) {
		if(((Course)o).name == name) return true;
		return false;
	}
}
