package recordrangers.services;

import java.sql.*;
import java.util.ArrayList;
import recordrangers.models.Student;

public class StudentSearch {
    private Connection connection;

    public StudentSearch() throws SQLException {
        connection = DatabaseConnection.getInstance().getConnection();
    }

    /**
     * Searches for students based on a search term and additional filter options.
     *
     * @param searchTerm The search term for first or last name.
     * @param sortBy The column name to sort by (e.g., "first_name", "studentId", or "enrollment_date").
     * @param minGrade The minimum grade filter (optional).
     * @param maxGrade The maximum grade filter (optional).
     * @param courseName The course name filter (optional).
     * @return An ArrayList of matching Student objects.
     * @throws SQLException if a database error occurs.
     */
    public ArrayList<Student> searchStudents(String searchTerm, String sortBy, String minGrade, String maxGrade, String courseName) throws SQLException {
        ArrayList<Student> students = new ArrayList<>();
        StringBuilder query = new StringBuilder();
        query.append("SELECT u.user_id AS studentId, ");
        query.append("u.first_name AS firstName, ");
        query.append("u.last_name AS lastName, ");
        query.append("u.email, ");
        query.append("s.enrollment_date, ");
        query.append("s.status ");
        query.append("FROM User u ");
        query.append("JOIN Student s ON u.user_id = s.student_id ");
        // Left join enrollments and course if filtering by grade or course.
        query.append("LEFT JOIN Enrollments e ON s.student_id = e.student_id ");
        query.append("LEFT JOIN Course c ON e.course_id = c.course_id ");
        query.append("WHERE (u.first_name LIKE ? OR u.last_name LIKE ?) ");
        
        if (minGrade != null && !minGrade.isEmpty()) {
            query.append("AND e.grade >= ? ");
        }
        if (maxGrade != null && !maxGrade.isEmpty()) {
            query.append("AND e.grade <= ? ");
        }
        if (courseName != null && !courseName.isEmpty()) {
            query.append("AND c.course_code LIKE ? ");
        }
        
        query.append("ORDER BY ").append(sortBy).append(" ASC");
        
        try (PreparedStatement stmt = connection.prepareStatement(query.toString())) {
            int index = 1;
            String pattern = "%" + searchTerm + "%";
            stmt.setString(index++, pattern);
            stmt.setString(index++, pattern);
            if (minGrade != null && !minGrade.isEmpty()) {
                stmt.setString(index++, minGrade);
            }
            if (maxGrade != null && !maxGrade.isEmpty()) {
                stmt.setString(index++, maxGrade);
            }
            if (courseName != null && !courseName.isEmpty()) {
                stmt.setString(index++, courseName);
            }
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int studentId = rs.getInt("studentId");
                    String firstName = rs.getString("firstName");
                    String lastName = rs.getString("lastName");
                    String email = rs.getString("email");
                    Date enrollmentDate = rs.getDate("enrollment_date");
                    Student.Status status = Student.getStatusFromResultSet(rs);
                    
                    // Create a Student object (note: password, profilePicture, and timestamps are not needed here)
                    Student student = new Student(studentId, firstName, lastName, email, "", "", null, null, enrollmentDate, status);
                    students.add(student);
                }
            }
        }
        return students;
    }
}
