package recordrangers.models;

import java.util.ArrayList;
import java.util.List;

public class StudentGradeReport {
    private final StudentInfo studentInfo;
    private final ArrayList<Enrollment> enrollments;

    public StudentGradeReport(StudentInfo studentInfo, ArrayList<Enrollment> enrollments) {
        this.studentInfo = studentInfo;
        this.enrollments = enrollments;
    }

    public int getStudentId() { return studentInfo.getStudentId(); }
    public String getFirstName() { return studentInfo.getFirstName(); }
    public String getLastName() { return studentInfo.getLastName(); }
    public String getEnrollmentDate() { return studentInfo.getEnrollmentDate(); }
    public ArrayList<Enrollment> getEnrollments() { return enrollments; }
}


