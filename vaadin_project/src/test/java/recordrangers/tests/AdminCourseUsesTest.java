package recordrangers.tests;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import recordrangers.services.AdminCourseUses;
import recordrangers.services.DatabaseConnection;

public class AdminCourseUsesTest {

   private static AdminCourseUses q;
   private static Connection connection;

   @BeforeAll
   public static void init() throws SQLException, IOException{
      connection = DatabaseConnection.getInstance().getConnection();
      q = new AdminCourseUses();
   }

   @Test
   public void testAddCourse() throws SQLException{
      // Add new course
    q.addCourse("Intro to Databases", "COSC 304", 3, "Introduction to SQL and relational databases", 120, "2024-09-10", 
    "2024-12-20", "2024 Winter T1", "Monday, Wesnesday", "9:30", "11:00", "Lib 304");

    // Verify the course was added
    String course = "COSC 304";
    String result = listAllCourses();
    assertTrue(result.contains(course));
   }

   @Test
   public void testEditCourse() throws SQLException{
    // Edit the course location
    q.editCourse("Intro to Databases", "COSC 304", 3, "Introduction to SQL and relational databases", 120, "2024-09-10", 
    "2024-12-20", "2024 Winter T1", "Monday, Wesnesday", "9:30", "11:00", "ASC 140");
    String sql = "SELECT location FROM Course WHERE course_code = 'COSC 304'";

    // Verify location was updated
    try (Statement stmt = connection.createStatement()) {
      ResultSet rst = stmt.executeQuery(sql);
      rst.next();
      String newLocation = rst.getString("location");
      assertEquals("ASC 140", newLocation);
    } 

   }

   @Test
   public void testDeleteCourse() throws SQLException{
      // Delete course
	  q.addCourse("Intro to Databases", "COSC 304", 3, "Introduction to SQL and relational databases", 120, "2024-09-10", 
			    "2024-12-20", "2024 Winter T1", "Monday, Wesnesday", "9:30", "11:00", "Lib 304");
	  assertTrue(listAllCourses().contains("COSC 304"));
      q.deleteCourse("COSC 304");

      // Verify course was deleted
      String course = "COSC 304";
      String result = listAllCourses();
      assertFalse(result.contains(course));
   }

   public static String listAllCourses() throws SQLException{
      String sql = "SELECT course_code, course_name FROM Course";
      String out = "";
      
      try (Statement stmt = connection.createStatement()) {
         ResultSet rst = stmt.executeQuery(sql);
         while (rst.next()) {
            String code = rst.getString(1);
            String name = rst.getString(2);
            out += code + " " + name + "\n";
            //System.out.println(out);
         }
      } catch (SQLException e) {
         System.err.println(e.getMessage());
      }
      return out;
}
}


