import java.sql.*;

public class Student {
    private int studentId;
    private int userId;
    private String fullName;
    private String email;
    private String phone;
    private String address;
    
    private static final String DB_URL = "jdbc:mysql://localhost:3306/your_database";
    private static final String DB_USER = "your_username";
    private static final String DB_PASSWORD = "your_password";


    public Student(){}

    public Student(int studentId, int userId, String fullName, String email, String phone, String address) {
        this.studentId = studentId;
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    public static String enrollStudent(int studentId, int courseId) {
        String queryCheckEnrollment = "SELECT * FROM enrollments WHERE student_id = ? AND course_id = ?";
        String queryCourseCapacity = "SELECT COUNT(*) AS enrolled, max_capacity FROM enrollments " +
                                     "JOIN courses ON enrollments.course_id = courses.course_id WHERE courses.course_id = ?";
        String queryEnroll = "INSERT INTO enrollments (student_id, course_id) VALUES (?, ?)";
        String queryWaitlist = "INSERT INTO waitlists (student_id, course_id) VALUES (?, ?)";
        
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement checkStmt = conn.prepareStatement(queryCheckEnrollment);
             PreparedStatement capacityStmt = conn.prepareStatement(queryCourseCapacity);
             PreparedStatement enrollStmt = conn.prepareStatement(queryEnroll);
             PreparedStatement waitlistStmt = conn.prepareStatement(queryWaitlist)) {
            
            // Check if student is already enrolled
            checkStmt.setInt(1, studentId);
            checkStmt.setInt(2, courseId);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) {
                return "Student is already enrolled in this course.";
            }
            
            // Check course capacity
            capacityStmt.setInt(1, courseId);
            rs = capacityStmt.executeQuery();
            if (rs.next()) {
                int enrolled = rs.getInt("enrolled");
                int maxCapacity = rs.getInt("max_capacity");
                
                if (enrolled < maxCapacity) {
                    // Enroll the student
                    enrollStmt.setInt(1, studentId);
                    enrollStmt.setInt(2, courseId);
                    enrollStmt.executeUpdate();
                    return "Student successfully enrolled.";
                } else {
                    // Add student to waitlist
                    waitlistStmt.setInt(1, studentId);
                    waitlistStmt.setInt(2, courseId);
                    waitlistStmt.executeUpdate();
                    return "Course is full. Student added to waitlist.";
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Database error: " + e.getMessage();
        }
        return "Enrollment process failed.";
    }

    public static void main(String[] args) {
        int studentId = 1;
        int courseId = 2;
        System.out.println(enrollStudent(studentId, courseId));
    }
}
