package recordrangers.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import recordrangers.models.Course;
import recordrangers.services.CourseDAO;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

@PageTitle("Course Adding")
@Route(value = "admin-adding-page", layout = AdminHomeView.class)
public class AdminCourseAddingView extends VerticalLayout {
    // Constants
    private static final List<String> DAYS_OF_WEEK_OPTIONS = Arrays.asList(
            "Monday-Wednesday", 
            "Tuesday-Thursday", 
            "Wednesday-Friday", 
            "Monday-Wednesday-Friday"
    );
    
    // Form fields
    private TextField courseNameField;
    private TextField courseCodeField;
    private TextArea descriptionField;
    private TextField termLabelField;
    private IntegerField creditsField;
    private IntegerField capacityField;
    private DatePicker startDatePicker;
    private DatePicker endDatePicker;
    private ComboBox<String> daysOfWeekComboBox;
    private TimePicker startTimePicker;
    private TimePicker endTimePicker;
    private TextField locationField;
    private Button submitButton;

    public AdminCourseAddingView() {
        initView();
    }

    private void initView() {
        setSpacing(true);
        setPadding(true);
        
        add(new H1("Create a New Course!"));
        
        initFormFields();
        layoutForm();
        setupValidation();
    }
    
    private void initFormFields() {
        courseNameField = new TextField("Course Name");
        courseCodeField = new TextField("Course Code (e.g. COSC 101)");
        descriptionField = new TextArea("Description");
        termLabelField = new TextField("Term Label (e.g. 2025 Winter 2025)");
        
        // Use IntegerField instead of TextField for numeric inputs
        creditsField = new IntegerField("Number of Credits");
        capacityField = new IntegerField("Capacity");
        
        startDatePicker = new DatePicker("Start Date");
        endDatePicker = new DatePicker("End Date");
        
        daysOfWeekComboBox = new ComboBox<>("Days of the Week");
        daysOfWeekComboBox.setItems(DAYS_OF_WEEK_OPTIONS);
        
        startTimePicker = new TimePicker("Start Time");
        endTimePicker = new TimePicker("End Time");
        
        locationField = new TextField("Location");
        
        submitButton = new Button("Create Course", event -> handleSubmit());
    }
    
    private void layoutForm() {
        // Grouping related fields
        HorizontalLayout courseInfoLayout = new HorizontalLayout(courseNameField, courseCodeField);
        courseInfoLayout.setWidthFull();
        
        HorizontalLayout dateLayout = new HorizontalLayout(startDatePicker, endDatePicker);
        dateLayout.setWidthFull();
        
        HorizontalLayout timeLayout = new HorizontalLayout(startTimePicker, endTimePicker);
        timeLayout.setWidthFull();
        
        HorizontalLayout numericsLayout = new HorizontalLayout(creditsField, capacityField);
        numericsLayout.setWidthFull();
        
        // Adding all components in logical groups
        add(
            courseInfoLayout,
            descriptionField,
            termLabelField,
            numericsLayout,
            dateLayout,
            daysOfWeekComboBox,
            timeLayout,
            locationField,
            submitButton
        );
        
        // Make description field wider
        descriptionField.setWidthFull();
    }
    
    private void setupValidation() {
        // Set required fields
        courseNameField.setRequired(true);
        courseCodeField.setRequired(true);
        descriptionField.setRequired(true);
        termLabelField.setRequired(true);
        creditsField.setRequired(true);
        capacityField.setRequired(true);
        startDatePicker.setRequired(true);
        endDatePicker.setRequired(true);
        daysOfWeekComboBox.setRequired(true);
        startTimePicker.setRequired(true);
        endTimePicker.setRequired(true);
        locationField.setRequired(true);
        
        // Add min/max values for numeric fields
        creditsField.setMin(0);
        creditsField.setMax(10);
        capacityField.setMin(1);
        
        // Date validation
        startDatePicker.addValueChangeListener(e -> validateDates());
        endDatePicker.addValueChangeListener(e -> validateDates());
        
        // Time validation
        startTimePicker.addValueChangeListener(e -> validateTimes());
        endTimePicker.addValueChangeListener(e -> validateTimes());
    }
    
    private void validateDates() {
        if (startDatePicker.getValue() != null && endDatePicker.getValue() != null) {
            if (startDatePicker.getValue().isAfter(endDatePicker.getValue())) {
                Notification.show("Start date cannot be after end date", 
                    3000, Notification.Position.TOP_CENTER);
                endDatePicker.setValue(null);
            }
        }
    }
    
    private void validateTimes() {
        if (startTimePicker.getValue() != null && endTimePicker.getValue() != null) {
            if (startTimePicker.getValue().isAfter(endTimePicker.getValue())) {
                Notification.show("Start time cannot be after end time", 
                    3000, Notification.Position.TOP_CENTER);
                endTimePicker.setValue(null);
            }
        }
    }

    private void handleSubmit() {
        if (!validateForm()) {
            return;
        }

        try {
            Course course = createCourseFromForm();
            saveCourse(course);
            Notification.show("Course created successfully!", 
                3000, Notification.Position.TOP_CENTER);
            clearForm();
        } catch (Exception e) {
            Notification.show("Error creating course: " + e.getMessage(), 
                5000, Notification.Position.TOP_CENTER);
        }
    }
    
    private boolean validateForm() {
        if (fieldsAreEmpty()) {
            Notification.show("Please fill in all required fields.", 
                3000, Notification.Position.TOP_CENTER);
            return false;
        }
        
        // Additional validation could be added here
        return true;
    }

    private Course createCourseFromForm() {
        return new Course(
            courseNameField.getValue(),
            courseCodeField.getValue(),
            creditsField.getValue(),
            descriptionField.getValue(),
            capacityField.getValue(),
            locationField.getValue(),
            startDatePicker.getValue(),
            endDatePicker.getValue(),
            termLabelField.getValue(),
            daysOfWeekComboBox.getValue(),
            startTimePicker.getValue(),
            endTimePicker.getValue()
        );
    }
    
    private void saveCourse(Course course) {
        try {
            CourseDAO.addCourse(course);
        } catch (Exception e) {
            throw new RuntimeException("Failed to save course: " + e.getMessage(), e);
        }
    }
    
    private void clearForm() {
        courseNameField.clear();
        courseCodeField.clear();
        descriptionField.clear();
        termLabelField.clear();
        creditsField.clear();
        capacityField.clear();
        startDatePicker.clear();
        endDatePicker.clear();
        daysOfWeekComboBox.clear();
        startTimePicker.clear();
        endTimePicker.clear();
        locationField.clear();
    }

    private boolean fieldsAreEmpty() {
        return courseNameField.isEmpty() || courseCodeField.isEmpty() || 
               descriptionField.isEmpty() || termLabelField.isEmpty() || 
               creditsField.isEmpty() || capacityField.isEmpty() ||
               startDatePicker.isEmpty() || endDatePicker.isEmpty() || 
               daysOfWeekComboBox.isEmpty() || startTimePicker.isEmpty() || 
               endTimePicker.isEmpty() || locationField.isEmpty();
    }
}