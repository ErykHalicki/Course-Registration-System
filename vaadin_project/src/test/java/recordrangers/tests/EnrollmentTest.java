package recordrangers.tests;

import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import recordrangers.models.Course;
import recordrangers.services.CourseDAO;
import recordrangers.services.CourseRegistration;
import recordrangers.services.LabRegistration;

public class EnrollmentTest {

    private CourseRegistration cr;
    private LabRegistration lr;

    @BeforeClass
    public void init() throws SQLException {
        this.cr = new CourseRegistration();
    }

    @Test
    public void testCourseEnrollment() throws SQLException {
        cr.registerStudent(3, 1);
        ArrayList<Course> courses = CourseDAO.getEnrolledCourses(3);
        boolean found = courses.stream().anyMatch(c -> 
            c.getCourseName().equals("Intro to Computer Science") &&
            c.getCourseCode().equals("COSC 101") &&
            c.getTermLabel().equals("2025 Winter T1")
        );
        Assert.assertTrue("Course should be enrolled", found);
    }

    @Test
    public void testLabEnrollment() throws SQLException {
        lr.registerStudentIntoLab(3, 1);
        ArrayList<Course> courses = CourseDAO.getEnrolledCourses(3);
        boolean found = courses.stream().anyMatch(c -> 
            c.getCourseName().equals("COSC 101 Lab LO1") &&
            c.getCourseCode().equals("COSC 101") && 
            c.getTermLabel().equals("2025 Winter T1")
        );
        Assert.assertTrue("Lab should be enrolled", found);
    }
}
