package recordrangers.services;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import recordrangers.models.Student;
import recordrangers.models.Student.Status;

public class StudentSearch {
    private final Connection con;

    public StudentSearch() throws SQLException {
        this.con = DatabaseConnection.getInstance().getConnection();
    }

    public ArrayList<Student> searchStudentsByName(String firstName, String lastName) throws SQLException {
        ArrayList<Student> students = new ArrayList<>();
        String sql = 
        "SELECT * " +
        "FROM Students " + 
        "WHERE firstName = ? AND lastName = ?";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            // To Do: allow for matching of 1 name or both
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            try (ResultSet rst = pstmt.executeQuery()) {
                while (rst.next()) {
                    students.add(new Student());
                }
            }
        }
        return students;
    }

    public ArrayList<Student> searchForStudents(String searchTerm, String sortBy, String minGrade, String maxGrade, String courseFilter) throws SQLException {
        // Returns the result set of students with attributes matching the display format in AdminStudentSearchView
        String query = buildQuery(searchTerm, sortBy, minGrade, maxGrade, courseFilter);
        ArrayList<Student> students = new ArrayList<>();

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            setQueryParameters(pstmt, searchTerm, minGrade, maxGrade, courseFilter);
            ResultSet rst = pstmt.executeQuery();

            while (rst.next()) {
                Student student = new Student();
                student.setStudentId(rst.getInt("student_id"));
                student.setFirstName(rst.getString("first_name"));
                student.setLastName(rst.getString("last_name"));
                student.setEmail(rst.getString("email"));
                student.setEnrollment_date(rst.getDate("enrollment_date"));
                student.setStatus(Status.valueOf(rst.getString("status")));
                students.add(student);
            }
        }
        return students;
    }

    private String buildQuery(String searchTerm, String sortBy, String minGrade, String maxGrade, String courseFilter) {
        StringBuilder query = new StringBuilder("SELECT s.student_id, u.first_name, u.last_name, u.email, s.enrollment_date, s.status ");
        query.append("FROM User as u ");
        query.append("JOIN Student as s ON u.user_id = s.student_id ");
        query.append("LEFT JOIN Enrollments as e ON s.student_id = e.student_id ");
        query.append("LEFT JOIN Course as c ON e.course_id = c.course_id ");
        query.append("WHERE 1=1 ");

        if (searchTerm != null && !searchTerm.isEmpty()) {
            query.append("AND (u.first_name LIKE ? OR u.last_name LIKE ? OR u.user_id = ?) ");
        }
        if (minGrade != null && !minGrade.isEmpty()) {
            query.append("AND e.grade <= ? ");
        }
        if (maxGrade != null && !maxGrade.isEmpty()) {
            query.append("AND e.grade >= ? ");
        }
        if (courseFilter != null && !courseFilter.isEmpty()) {
            query.append("AND c.course_name = ? ");
        }

        query.append("ORDER BY ").append(sortBy).append(" ASC");

        return query.toString();
    }

    private void setQueryParameters(PreparedStatement statement, String searchTerm, String minGrade, String maxGrade, String courseFilter) throws SQLException {
        int parameterIndex = 1;

        if (searchTerm != null && !searchTerm.isEmpty()) {
            statement.setString(parameterIndex++, "%" + searchTerm + "%");
            statement.setString(parameterIndex++, "%" + searchTerm + "%");
            statement.setString(parameterIndex++, searchTerm);
        }
        if (minGrade != null && !minGrade.isEmpty()) {
            statement.setString(parameterIndex++, minGrade);
        }
        if (maxGrade != null && !maxGrade.isEmpty()) {
            statement.setString(parameterIndex++, maxGrade);
        }
        if (courseFilter != null && !courseFilter.isEmpty()) {
            statement.setString(parameterIndex++, courseFilter);
        }
    }
}
