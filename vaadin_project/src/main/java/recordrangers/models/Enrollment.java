package recordrangers.models;

public class Enrollment {
    private final String courseCode;
    private final String courseName;
    private final int credits;
    private final String grade;
    private final String status;

    public Enrollment(String courseCode, String courseName, int credits, String grade, String status) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.credits = credits;
        this.grade = grade;
        this.status = status;
    }

    public String getCourseCode() { return courseCode; }
    public String getCourseName() { return courseName; }
    public int getCredits() { return credits; }
    public String getGrade() { return grade; }
    public String getStatus() { return status; }
}

