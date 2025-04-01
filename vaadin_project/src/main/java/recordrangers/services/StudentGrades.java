package recordrangers.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import recordrangers.models.Enrollment;
import recordrangers.models.StudentGradeReport;
import recordrangers.models.StudentInfo;

@Service
public class StudentGrades {

    private final Connection con;

    public StudentGrades() throws SQLException {
        this.con = DatabaseConnection.getInstance().getConnection();
    }

    public StudentGradeReport getStudentGrades(String searchTerm) throws SQLException {
        StudentInfo studentInfo = getStudentInfo(searchTerm);
        if(studentInfo == null) {
            return null;
        }

        ArrayList<Enrollment> enrollments = getStudentEnrollments(studentInfo.getStudentId());

        return new StudentGradeReport(studentInfo, enrollments);
    }

    private StudentInfo getStudentInfo(String searchTerm) throws SQLException {
        String sql = "SELECT s.student_id, u.first_name, u.last_name, s.enrollment_date " +
                    "FROM Student as s JOIN User as u ON s.student_id = u.user_id " +
                    "WHERE student_id = ? OR CONCAT(first_name, ' ', last_name) LIKE ? LIMIT 1";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            try {
                int studentId = Integer.parseInt(searchTerm);
                pstmt.setInt(1, studentId);
                String name = "%" + searchTerm + "%";
                pstmt.setString(2, name);
            } catch (NumberFormatException e) {
                pstmt.setInt(1, -1);
                String name = "%" + searchTerm + "%";
                pstmt.setString(2, name);
            }
            
            ResultSet rst = pstmt.executeQuery();
            if (rst.next()) {
                return new StudentInfo(
                    rst.getInt("student_id"), 
                    rst.getString("first_name"),
                    rst.getString("last_name"),
                    rst.getString("enrollment_date")
                );
            }
        }
        return null;
    }

    private ArrayList<Enrollment> getStudentEnrollments(int studentId) throws SQLException {
        ArrayList<Enrollment> enrollments = new ArrayList<>();
        
        String sql = "SELECT c.course_code, c.course_name, c.num_credits, e.grade, e.status " +
                     "FROM Enrollments as e JOIN Course as c ON e.course_id = c.course_id " +
                     "WHERE e.student_id = ? " +
                     "ORDER BY c.course_code";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, studentId);
            ResultSet rst = pstmt.executeQuery();
            while(rst.next()) {
                enrollments.add(new Enrollment(
                    rst.getString("course_code"),
                    rst.getString("course_name"),
                    rst.getInt("num_credits"),
                    rst.getString("grade"),
                    rst.getString("status")
                ));
            }
        }

        return enrollments;
    }

    public String generateCSVReport(StudentGradeReport report) {
        if (report == null || report.getEnrollments().isEmpty()) {
            return "";
        }

        StringBuilder csv = new StringBuilder();
        csv.append("Student ID, Student Name\n");
        csv.append(report.getStudentId()).append(",");
        csv.append("\"").append(report.getFirstName()).append(" ").append(report.getLastName()).append("\"\n\n");

        csv.append("Course Code, Course Name, Credits, Grade, Status\n");

        for(Enrollment enrollment : report.getEnrollments()) {
            csv.append(enrollment.getCourseCode()).append(",");
            csv.append(enrollment.getCourseName()).append(",");
            csv.append(enrollment.getCredits()).append(",");
            csv.append(enrollment.getGrade()).append(",");
            csv.append(enrollment.getStatus()).append("\n");
        }

        return csv.toString();
    }
}

