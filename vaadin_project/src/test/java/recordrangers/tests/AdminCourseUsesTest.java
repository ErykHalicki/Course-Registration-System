package recordrangers.tests;
import java.time.LocalDate;
import java.time.LocalTime;
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

import recordrangers.models.Course;
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
	   
	   q.addCourse(new Course(
			    "Intro to Databases", // courseName
			    "COSC 304", // courseCode
			    3, // numCredits
			    "An introduction to relational databases, SQL, and database design.", // description
			    50, // maxCapacity
			    "Lib 304", // location
			    LocalDate.of(2025, 9, 6), // startDate
			    LocalDate.of(2025, 12, 15), // endDate
			    "Fall 2025", // termLabel
			    "MWF", // days (Monday, Wednesday, Friday)
			    LocalTime.of(10, 0), // startTime (10:00 AM)
			    LocalTime.of(11, 30) // endTime (11:30 AM)
			));

    // Verify the course was added
    String course = "COSC 304";
    String result = listAllCourses();
    assertTrue(result.contains(course));
   }

   @Test
   public void testEditCourse() throws SQLException{
    // Edit the course location
	   q.editCourse(new Course(
			    "Intro to Databases", // courseName
			    "COSC 304", // courseCode
			    3, // numCredits
			    "An introduction to relational databases, SQL, and database design.", // description
			    50, // maxCapacity
			    "ASC 140", // location
			    LocalDate.of(2025, 9, 6), // startDate
			    LocalDate.of(2025, 12, 15), // endDate
			    "Fall 2025", // termLabel
			    "MWF", // days (Monday, Wednesday, Friday)
			    LocalTime.of(10, 0), // startTime (10:00 AM)
			    LocalTime.of(11, 30) // endTime (11:30 AM)
			));
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
	   
	   q.addCourse(new Course(
			    "Intro to Databases", // courseName
			    "COSC 304", // courseCode
			    3, // numCredits
			    "An introduction to relational databases, SQL, and database design.", // description
			    50, // maxCapacity
			    "Lib 304", // location
			    LocalDate.of(2025, 9, 6), // startDate
			    LocalDate.of(2025, 12, 15), // endDate
			    "Fall 2025", // termLabel
			    "MWF", // days (Monday, Wednesday, Friday)
			    LocalTime.of(10, 0), // startTime (10:00 AM)
			    LocalTime.of(11, 30) // endTime (11:30 AM)
			));
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


