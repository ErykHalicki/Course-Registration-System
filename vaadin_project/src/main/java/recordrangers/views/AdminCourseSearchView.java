package recordrangers.views;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import recordrangers.models.Course;
import recordrangers.services.CourseDAO;

@PageTitle("Course Management")
@Route(value = "admin-course-search", layout = AdminHomeView.class)
public class AdminCourseSearchView extends VerticalLayout {

    private Grid<Course> courseGrid = new Grid<>(Course.class);
    private TextField searchField = new TextField();
    private CourseDAO databaseInterface = new CourseDAO();
    private List<Course> allCourses;

    public AdminCourseSearchView() throws SQLException {
        allCourses = databaseInterface.getAllCourses();
        configureSearchField();
        configureGrid();
        
        Button addButton = new Button("Add Course", event -> {
            try {
                openCourseDialog(new Course(null), false);
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
        
        HorizontalLayout searchLayout = new HorizontalLayout(searchField, addButton);
        searchLayout.setSpacing(true);
        
        add(searchLayout, courseGrid);
    }

    private void configureSearchField() {
        searchField.setValueChangeMode(ValueChangeMode.EAGER);
        searchField.setPlaceholder("Enter course name...");
        searchField.setClearButtonVisible(true);
        searchField.addValueChangeListener(event -> updateGrid(event.getValue()));
    }

    private void configureGrid() {
        courseGrid.setColumns("courseId", "courseName", "courseCode", "numCredits", "termLabel", "startDate", "endDate");
        courseGrid.setItems(allCourses);
        
        courseGrid.addComponentColumn(course -> {
            Button editButton = new Button("Edit", event -> {
                try {
                    openCourseDialog(course, true);
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            });
            Button deleteButton = new Button("Delete", event -> {
                try {
                    deleteCourse(course);
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            });
            return new HorizontalLayout(editButton, deleteButton);
        }).setHeader("Actions");
    }


    private void updateGrid(String searchQuery) {

        if (searchQuery == null || searchQuery.trim().isEmpty()) {
            courseGrid.setItems(allCourses);
        } else {
            String lowerCaseQuery = searchQuery.toLowerCase();
            courseGrid.setItems(allCourses.stream()
                    .filter(course -> course.getCourseCode().toLowerCase().contains(lowerCaseQuery))
                    .collect(Collectors.toList()));
        }
    }

    private void openCourseDialog(Course course, boolean isEdit) throws SQLException {
        Dialog dialog = new Dialog();
        FormLayout formLayout = new FormLayout();
        Binder<Course> binder = new Binder<>(Course.class);
        
        TextField courseNameField = new TextField("Course Name");
        TextField courseCodeField = new TextField("Course Code");
        TextField numCreditsField = new TextField("Credits");
        
        binder.bind(courseNameField, Course::getCourseName, Course::setCourseName);
        binder.bind(courseCodeField, Course::getCourseCode, Course::setCourseCode);
        binder.bind(numCreditsField, c -> String.valueOf(c.getNumCredits()), (c, value) -> c.setNumCredits(Integer.parseInt(value)));
        
        binder.setBean(course);
        formLayout.add(courseNameField, courseCodeField, numCreditsField);
        
        Button saveButton = new Button("Save", event -> {
            if (isEdit) {
                databaseInterface.updateCourse(course);
            } else {
                databaseInterface.addCourse(course);
                allCourses.add(course);
            }
            updateGrid(searchField.getValue());
            dialog.close();
        });
        
        Button cancelButton = new Button("Cancel", event -> dialog.close());
        
        dialog.add(formLayout, new HorizontalLayout(saveButton, cancelButton));
        dialog.open();
    }

    private void deleteCourse(Course course) throws SQLException {
        databaseInterface.deleteCourse(course.getCourseCode());
        allCourses.remove(course);
        updateGrid(searchField.getValue());
    }
}