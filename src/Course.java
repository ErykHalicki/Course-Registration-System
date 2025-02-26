
class Course {
    public int courseId;
    public String courseCode;
    public String courseName;
    public int numCredits;
    public String description;
    public int maxCapacity;
    public String schedule;
    public String location;
       
	public Course(){

    }
	public Course(String courseCode) {
        this.courseCode = courseCode;
    }
    public Course(int courseId, String courseCode, String courseName, int maxCapacity, String schedule) {
        this.courseId = courseId;
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.maxCapacity = maxCapacity;
        this.schedule = schedule;
    }
    public int getCourseId() {
        return courseId;
    }
    public String getCourseCode() {
        return courseCode;
    }
    public String getCourseName() {
        return courseName;
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
    public String getSchedule() {
        return schedule;
    }
    public String getLocation() {
        return location;
    }
    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }
    public void setCourseName(String courseName) {
        this.courseName = courseName;
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
               "Course Name: " + courseName + "\n" +
               "Max Capacity: " + maxCapacity + "\n" +
               "Schedule: " + schedule;
    }
}
