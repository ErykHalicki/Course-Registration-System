package recordrangers.models;

public class StudentInfo {
    private final int studentId;
    private final String firstName;
    private final String lastName;
    private final String enrollmentDate;

    public StudentInfo(int id, String first, String last, String enrollmentDate) {
        this.studentId = id;
        this.firstName = first;
        this.lastName = last;
        this.enrollmentDate = enrollmentDate;
    }

    public int getStudentId(){ return studentId; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEnrollmentDate() { return enrollmentDate; }
}
