package recordrangers.models;

public class Course {
    public int courseId;
    public String courseName;
    public String courseCode;
    public int numCredits;
    public String description;
    public int maxCapacity;
    public String schedule;
    public String location;
    public int enrollment;
       
	public Course(){

    }
	public Course(String courseCode) {
        this.courseCode = courseCode;
    }
	public Course(String courseCode, int max, int current) {
        this.courseCode = courseCode;
        this.maxCapacity = max;
        this.enrollment = current;
    }
    public Course(int courseId, String courseCode, String courseName, int maxCapacity, String schedule) {
        this.courseId = courseId;
        this.courseCode = courseCode;
        this.maxCapacity = maxCapacity;
        this.schedule = schedule;
    }
    public int getCourseId() {
        return courseId;
    }
    public String getCourseName() {
        return courseName;
    }
    public String getCourseCode() {
        return courseCode;
    }
    public int getNumCredits() {
        return numCredits;
    }
    public String getDescription() {
        return description;
    }
    public int getMaxCapacity() {
        return maxCapacity;
    }
    public int getEnrollment() {
        return enrollment;
    }
    public String getSchedule() {
        return schedule;
    }
    public String getLocation() {
        return location;
    }
    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }
    public void setNumCredits(int numCredits) {
        this.numCredits = numCredits;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }
    public void setEnrollment(int enrollment) {
        this.enrollment = enrollment;
    }
    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    @Override
    public String toString() {
        return "Course ID: " + courseId + "\n" +
               "Course Code: " + courseCode + "\n" +
               "Max Capacity: " + maxCapacity + "\n" +
               "Schedule: " + schedule;
    }
}
