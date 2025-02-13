package recordrangers.models;
import java.util.List;

public class Student extends User{
	enum Standing {
		Good,
		Warning,
		Probation,
		Suspension
	}
	
	Standing academicStanding;
	
	private int yearLevel;
	List<Course> enrolledCourses; 
    private int studentId;
    private String fullName;
    private String email;
    private String phone;
    private String address;

    // Getters and Setters
    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
    public int getYearLevel() {
        return yearLevel;
    }

    public void setYearLevel(int year) {
        this.yearLevel = year;
    }
    
}