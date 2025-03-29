package recordrangers.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import recordrangers.models.Course;
import recordrangers.models.Student;
import recordrangers.services.CourseDAO;
import recordrangers.services.StudentSearch;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@PageTitle("Admin Student Search")
@Route(value = "admin-student-search", layout = AdminHomeView.class)
public class AdminStudentSearchView extends VerticalLayout {
    // Constants
    private static final List<String> SORT_OPTIONS = Arrays.asList("first_name", "last_name", "studentId", "enrollment_date", "email", "status");
    private static final List<String> DISPLAY_COLUMNS = Arrays.asList("studentId", "firstName", "lastName", "email", "enrollment_date", "status");
    
    // UI Components
    private final TextField searchField = new TextField("Search by Name");
    private final ComboBox<String> sortByCombo = new ComboBox<>("Sort By");
    private final ComboBox<String> courseNameCombo = new ComboBox<>("Course");
    private final Button searchButton = new Button("Search");
    private final Button clearButton = new Button("Clear Filters");
    private final Grid<Student> studentGrid = new Grid<>(Student.class);
    
    // Services
    private StudentSearch studentSearch;

    public AdminStudentSearchView() {
        configureView();
        initializeService();
        setupFilters();
        setupGrid();
        setupEventListeners();
        loadInitialData();
    }
    
    private void configureView() {
        setSizeFull();
        setPadding(true);
        setSpacing(true);
        
        H2 title = new H2("Admin Student Search");
        add(title);
    }
    
    private void initializeService() {
        try {
            studentSearch = new StudentSearch();
        } catch (SQLException ex) {
            handleException("Error initializing Student Search Service", ex);
        }
    }
    
    private void setupFilters() {
        // Configure search field
        searchField.setPlaceholder("Enter student name...");
        searchField.setClearButtonVisible(true);
        searchField.setValueChangeMode(ValueChangeMode.EAGER);
        
        // Configure sort combo
        sortByCombo.setItems(SORT_OPTIONS);
        sortByCombo.setValue("first_name");
        sortByCombo.setPlaceholder("Select sort field");
        
        // Load course options dynamically if possible
        try {
        	List<String> courseNames = CourseDAO.getAllCourses().stream()
        	        .map(course -> course.getCourseCode()) // or .getCourseName() depending on what you want to display
        	        .distinct()
        	        .sorted()
        	        .collect(Collectors.toList());
        	courseNameCombo.setItems(courseNames);
        } catch (SQLException ex) {
            // Fallback to static list
            courseNameCombo.setItems("MATH", "COSC", "ENG", "ECON");
            handleException("Could not load course list", ex);
        }
        courseNameCombo.setPlaceholder("Select course");
        
        // Configure buttons
        searchButton.getStyle().set("margin-top", "16px");
        clearButton.getStyle().set("margin-top", "16px");
        
        // Create horizontal layout for buttons
        HorizontalLayout buttonLayout = new HorizontalLayout(searchButton, clearButton);
        
        // Create horizontal layout for filters to maximize space
        HorizontalLayout filtersLayout = new HorizontalLayout(
                searchField,
                sortByCombo,
                courseNameCombo
        );
        filtersLayout.setWidthFull();
        
        // Add components to main layout
        add(filtersLayout, buttonLayout);
    }
    
    private void setupGrid() {
        // Configure grid
        studentGrid.setColumns(DISPLAY_COLUMNS.toArray(new String[0]));
        studentGrid.setSizeFull();
        
        // Customize column headers if needed
        studentGrid.getColumnByKey("studentId").setHeader("Student ID");
        studentGrid.getColumnByKey("firstName").setHeader("First Name");
        studentGrid.getColumnByKey("lastName").setHeader("Last Name");
        studentGrid.getColumnByKey("email").setHeader("Email");
        studentGrid.getColumnByKey("enrollment_date").setHeader("Enrollment Date");
        studentGrid.getColumnByKey("status").setHeader("Status");
        
        // Allow resizing columns
        studentGrid.getColumns().forEach(column -> column.setResizable(true));
        
        // Add grid to main layout
        add(studentGrid);
    }
    
    private void setupEventListeners() {
        // Set search button click listener
        searchButton.addClickListener(e -> updateGrid());
        
        // Add clear button listener
        clearButton.addClickListener(e -> clearFilters());
        
        // Add listener for search field for real-time searching
        searchField.addValueChangeListener(e -> {
            if (e.getValue().length() >= 3 || e.getValue().isEmpty()) {
                updateGrid();
            }
        });
        
        // Add listeners for combo boxes
        sortByCombo.addValueChangeListener(e -> updateGrid());
        courseNameCombo.addValueChangeListener(e -> updateGrid());
    }
    
    private void loadInitialData() {
        updateGrid();
    }
    
    /**
     * Clears all filter fields and reloads the grid
     */
    private void clearFilters() {
        searchField.clear();
        sortByCombo.setValue("first_name");
        courseNameCombo.clear();
        updateGrid();
    }

    /**
     * Gathers filter values and updates the grid with the search results.
     */
    private void updateGrid() {
        String searchTerm = searchField.getValue().trim();
        String sortBy = sortByCombo.getValue();
        String courseName = courseNameCombo.getValue() != null ? courseNameCombo.getValue() : "";
        
        try {
            ArrayList<Student> students = studentSearch.searchStudents(searchTerm, sortBy, courseName);
            studentGrid.setItems(students);
            
            // Show result count
            if (students.isEmpty()) {
                Notification.show("No students match your search criteria", 
                    3000, Notification.Position.BOTTOM_START);
            } else {
                studentGrid.getDataProvider().refreshAll();
            }
        } catch (SQLException ex) {
            handleException("Error searching for students", ex);
        }
    }
    
    /**
     * Centralized exception handling
     */
    private void handleException(String message, Exception ex) {
        ex.printStackTrace();
        Notification.show(message + ": " + ex.getMessage(), 
            5000, Notification.Position.MIDDLE);
    }
}