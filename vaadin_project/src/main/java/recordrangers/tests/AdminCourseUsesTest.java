package recordrangers.models;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import java.sql.*;
public class AdminCourseUsesTest {

   private static AdminCourseUses q;
   private static Connection con = ConnectionManager.getConnection();

   @BeforeClass
   public static void init() {
      q = new AdminCourseUses();
   }

   @Test
   public void testAddCourse() throws SQLException{
      // Add new course
    q.addCourse("Intro to Databases", "COSC 304", 3, "Introduction to SQL and relational databases", 120, "2024-09-10", 
    "2024-12-20", "2024 Winter T1", "Monday, Wesnesday", "9:30", "11:00", "Lib 304");

    // Verify the course was added
    String course = "COSC 304 Intro to Databases";
    String result = listAllCourses();
    Assert.assertTrue(result.contains(course));
   }

   @Test
   public void testEditCourse() throws SQLException{
    // Edit the course location
    q.editCourse("Intro to Databases", "COSC 304", 3, "Introduction to SQL and relational databases", 120, "2024-09-10", 
    "2024-12-20", "2024 Winter T1", "Monday, Wesnesday", "9:30", "11:00", "ASC 140");
    String sql = "SELECT location FROM Courses WHERE code = 'COSC 304'";

    // Verify location was updated
    try (Statement stmt = con.createStatement()) {
      ResultSet rst = stmt.executeQuery(sql);
      String newLocation = rst.getString("location");
      Assert.assertEquals("ASC 140", newLocation);
    } 

   }

   @Test
   public void testDeleteCourse() throws SQLException{
      // Delete course
      q.deleteCourse("COSC 304");

      // Verify course was deleted
      String course = "COSC 304 Intro to Databases";
      String result = listAllCourses();
      Assert.assertFalse(result.contains(course));
   }

   public static String listAllCourses() throws SQLException{
      String sql = "SELECT code, name FROM Courses";
      String out = "";
      
      try (Statement stmt = con.createStatement()) {
         ResultSet rst = stmt.executeQuery(sql);
         while (rst.next()) {
            String code = rst.getString(1);
            String name = rst.getString(2);
            out += code + " " + name + "\n";
         }
      } catch (SQLException e) {
         System.err.println(e.getMessage());
      }
      return out;
   }
}
