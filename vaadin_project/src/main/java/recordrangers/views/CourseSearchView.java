package recordrangers.views;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import recordrangers.models.Course;
import recordrangers.services.CourseDAO;

@PageTitle("Course Search")
@Route(value = "course-search", layout = StudentHomeView.class)
public class CourseSearchView extends VerticalLayout {

    protected Grid<Course> courseGrid = new Grid<>(Course.class);
    protected TextField searchField = new TextField();
    List<Course> allCourses;

    public CourseSearchView() throws SQLException {
        // Configure search field
    	CourseDAO databaseInterface = new CourseDAO();
    	
    	allCourses = databaseInterface.getAllCourses();
    	
    	searchField.setValueChangeMode(ValueChangeMode.EAGER);
        searchField.setPlaceholder("Enter course name...");
        searchField.setClearButtonVisible(true);

        // Add real-time filtering (event triggers on text input change)
        searchField.addValueChangeListener(event -> updateGrid(event.getValue()));

        // Layout for search components
        HorizontalLayout searchLayout = new HorizontalLayout(searchField);
        searchLayout.setSpacing(true);

        // Configure grid
        courseGrid.setColumns("courseName", "courseCode", "numCredits", "termLabel", "startTime", "endTime");
        courseGrid.setSizeUndefined();
        courseGrid.setItems(searchCoursesByString("")); // Initial dummy data

        // Add components to the layout
        add(searchLayout, courseGrid);
    }

    private void updateGrid(String searchQuery) {
        // Filters the dummy courses based on searchQuery
        courseGrid.setItems(searchCoursesByString(searchQuery));
        courseGrid.setSizeUndefined();
    }

    private List<Course> searchCoursesByString(String searchQuery) {
        // If searchQuery is empty, return all courses
        if (searchQuery == null || searchQuery.trim().isEmpty()) {
            return allCourses;
        }

        // Convert search query to lowercase for case-insensitive search
        String lowerCaseQuery = searchQuery.toLowerCase();
        
        // Filter courses where the name contains the search query
        return allCourses.stream()
                .filter(course -> course.getCourseCode().toLowerCase().contains(lowerCaseQuery))
                .collect(Collectors.toList());
    }
    public TextField getSearchField() {
        return searchField;
    }

    public Grid<Course> getCourseGrid() {
        return courseGrid;
    }
}