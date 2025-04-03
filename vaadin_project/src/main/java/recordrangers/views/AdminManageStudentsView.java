package recordrangers.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import recordrangers.models.Course;
import recordrangers.models.Student;
import recordrangers.services.StudentDAO;
import recordrangers.services.CourseDAO;
import recordrangers.services.CourseRegistration;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@PageTitle("Student Profile Management")
@Route(value = "admin-student-management", layout = AdminHomeView.class)
public class AdminManageStudentsView extends VerticalLayout {

    private StudentDAO studentDAO;
    private CourseDAO courseDAO;
    private Grid<Student> studentGrid = new Grid<>(Student.class);

    public AdminManageStudentsView() throws SQLException{
        try {
            // Instantiate the DAOs
            studentDAO = new StudentDAO();
            courseDAO = new CourseDAO();

            // Fetch all students and courses
            List<Student> students = studentDAO.getAllStudents();
            List<Course> courses = courseDAO.getAllCourses();

            // Configure the student grid
            studentGrid.setItems(students);
            studentGrid.removeAllColumns();
            studentGrid.addColumn(Student::getStudentId).setHeader("Student ID");
            studentGrid.addColumn(student -> student.getFirstName() + " " + student.getLastName())
                    .setHeader("Name");
            studentGrid.addColumn(Student::getEmail).setHeader("Email");
            studentGrid.addColumn(Student::getEnrollment_date).setHeader("Enrollment Date");
            studentGrid.addColumn(student -> student.getStatus().toString()).setHeader("Status");

            // Add a component column with enrollment and unenrollment actions
            studentGrid.addComponentColumn(student -> {
                VerticalLayout actionsLayout = new VerticalLayout();

                // --- Enrollment Section ---
                ComboBox<Course> enrollCombo = new ComboBox<>("Enroll in Course");
                enrollCombo.setItems(courses);
                enrollCombo.setItemLabelGenerator(course ->
                        course.getCourseCode() + " - " + course.getCourseName());

                Button enrollButton = new Button("Enroll", event -> {
                    Course selectedCourse = enrollCombo.getValue();
                    if (selectedCourse != null) {
                        // Use StudentDAO.enrollStudent to enroll the student.
                        String result;
						try {
							result = CourseRegistration.registerStudent(student.getStudentId(), selectedCourse.getCourseId());
							Notification.show(result);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                        
                        // Optionally refresh the grid or enrollment details.
                    } else {
                        Notification.show("Please select a course to enroll in.");
                    }
                });
                actionsLayout.add(enrollCombo, enrollButton);

                // --- Unenrollment Section ---
                List<Course> enrolledCourses;
                try {
                    // Fetch the courses the student is currently enrolled in.
                    enrolledCourses = CourseDAO.getEnrolledCourses(student.getStudentId());
                } catch (SQLException e) {
                    enrolledCourses = new ArrayList<>();
                    e.printStackTrace();
                }

                ComboBox<Course> unenrollCombo = new ComboBox<>("Unenroll from Course");
                unenrollCombo.setItems(enrolledCourses);
                unenrollCombo.setItemLabelGenerator(course ->
                        course.getCourseCode() + " - " + course.getCourseName());

                Button unenrollButton = new Button("Unenroll", event -> {
                    Course selectedCourse = unenrollCombo.getValue();
                    if (selectedCourse != null) {
                        // Call the new unenrollStudent method from StudentDAO.
                        String result = StudentDAO.unenrollStudent(student.getStudentId(), selectedCourse.getCourseId());
                        Notification.show(result);
                        // Optionally refresh the grid or enrollment details.
                    } else {
                        Notification.show("Please select a course to unenroll from.");
                    }
                });
                actionsLayout.add(unenrollCombo, unenrollButton);

                return actionsLayout;
            }).setHeader("Actions");

            add(studentGrid);

        } catch (SQLException ex) {
            Notification.show("Database error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
