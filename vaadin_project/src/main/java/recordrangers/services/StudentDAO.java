package recordrangers.services;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import recordrangers.models.Student;
import recordrangers.models.Student.Status;

public class StudentDAO{
    private static Connection connection;
        
        public StudentDAO() throws SQLException{
            StudentDAO.connection =  DatabaseConnection.getInstance().getConnection();
        }
        public StudentDAO(Connection connection){
            StudentDAO.connection = connection;
        }
    
    @SuppressWarnings("CallToPrintStackTrace")
        public static String enrollStudent(int studentId, int courseId) {
            String queryCheckEnrollment = "SELECT * FROM enrollments WHERE student_id = ? AND course_id = ?";
            String queryCourseCapacity = "SELECT COUNT(*) AS enrolled, max_capacity FROM enrollments " +
                                         "JOIN courses ON enrollments.course_id = courses.course_id WHERE courses.course_id = ?";
            String queryEnroll = "INSERT INTO enrollments (student_id, course_id) VALUES (?, ?)";
            String queryWaitlist = "INSERT INTO waitlists (student_id, course_id) VALUES (?, ?)";
            
            try (PreparedStatement checkStmt = connection.prepareStatement(queryCheckEnrollment);
             PreparedStatement capacityStmt = connection.prepareStatement(queryCourseCapacity);
             PreparedStatement enrollStmt = connection.prepareStatement(queryEnroll);
             PreparedStatement waitlistStmt = connection.prepareStatement(queryWaitlist)) {
            
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
    public ArrayList<Student> getAllStudents() throws SQLException {
        ArrayList<Student> students = new ArrayList<>();
        String query = "SELECT * FROM User JOIN Student ON student_id = user_id;";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int userId = rs.getInt("user_id");
                int studentId = rs.getInt("student_id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String profilePicture = rs.getString("profile_picture");
                Timestamp timeCreated = rs.getTimestamp("time_created");
                Timestamp timeUpdated = rs.getTimestamp("time_updated");
                Date enrollment_date = rs.getDate("enrollment_date");
                Status status = Student.getStatusFromResultSet(rs);
                students.add(new Student(userId, firstName, lastName, email, password, profilePicture, timeCreated, timeUpdated, enrollment_date, status));
            }
        }
        return students;
    }
public static void main(String[] args) {
    try {
        StudentDAO dao = new StudentDAO();
        ArrayList<Student> students = dao.getAllStudents();
            System.out.println(students);

    } catch (SQLException e) {
        e.printStackTrace();
    }
}
}
