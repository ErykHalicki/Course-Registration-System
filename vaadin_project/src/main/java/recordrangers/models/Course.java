package recordrangers.models;

import java.util.List;
import java.util.ArrayList;

public class Course {
    private int courseId;
    private String courseCode;
    private String courseName;
    private int maxCapacity;
    private String schedule;
    private int enrollment;
    private List<Student> enrolledStudents;
    private List<Student> waitlistedStudents;

    public Course() {
    	
    }
    
    public Course (String courseName, int maxCapacity, int enrolledStudentsAmount) {
    	this.courseName = courseName;
    	this.maxCapacity = maxCapacity;
    	this.enrolledStudents = new ArrayList<Student>();
    	for(int i = 0; i < enrolledStudentsAmount; i++) {
    		this.enrolledStudents.add(new Student()); // adding dummy students
    	}
    	enrollment = enrolledStudentsAmount;
    }
    
    // Getters and Setters
    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }
    
    public int getEnrollment() {
        return enrollment;
    }

    public void setEnrollment(int enrollment) {
        this.enrollment = maxCapacity;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }
}