import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDatabaseExporter {
    private static Connection connection;

    public StudentDatabaseExporter() throws SQLException{
       StudentDatabaseExporter.connection = DatabaseConnection.getConnection();
    }
    public static List<Student> fetchStudentsFromDB() {
        List<Student> students = new ArrayList<>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM students")) { // Selects everything from STUDENTS database.
                while (rs.next()) {

                students.add(new Student(
                        rs.getInt("student_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getBytes("profile_picture"),
                        rs.getTimestamp("time_created"),
                        rs.getTimestamp("time_updated"),
                        rs.getDate("enrollment_date"),
                        Student.Status.valueOf(rs.getString("status"))
                ));
                }
        } catch (Exception e) {
            e.printStackTrace();
            }
        return students;
    }

    public static void writeStudentsToFile(List<Student> students, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Student student : students) {
                writer.write(student.toString());
                writer.newLine();
            }
            System.out.println("Student data successfully written to " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        List<Student> students = fetchStudentsFromDB();
        writeStudentsToFile(students, "TextFileName.txt"); // text file to export to goes here.
    }
}
