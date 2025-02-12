import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class StudentDatabaseExporter {
    
    public static List<Student> fetchStudentsFromDB() {
        List<Student> students = new ArrayList<>();
        String url = "jdbc:mysql://"; // JDBC connection once database is set up. 
        String user = ""; // userid of database once set up.
        String password = ""; // password of userid once database set up.
        
        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM students")) { // Selects everything from STUDENTS database.

            while (rs.next()) {
                students.add(new Student());
                // The getters will change based on Student attributes in the database.
                // students.add(new Student(
                //     rs.getInt("id"),       
                //     rs.getString("name"), 
                //     rs.getInt("age"), 
                //     rs.getString("grade")));
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
