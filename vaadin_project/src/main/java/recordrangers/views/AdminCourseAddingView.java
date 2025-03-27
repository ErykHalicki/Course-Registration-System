package recordrangers.views;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import recordrangers.models.Course;
import recordrangers.services.AdminCourseUses;
import recordrangers.services.CourseDAO;

import com.vaadin.flow.component.html.H1;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

@PageTitle("Course Adding")
@Route(value = "admin-adding-page", layout = AdminHomeView.class)
public class AdminCourseAddingView extends VerticalLayout {
    private TextField courseNameField;
    private TextField courseCodeField;
    private TextArea descriptionField;
    private TextField termLabelField;
    private TextField creditsField;
    private TextField capacityField;
    private DatePicker startDatePicker;
    private DatePicker endDatePicker;
    private ComboBox<String> daysOfWeekComboBox;
    private TimePicker startTimePicker;
    private TimePicker endTimePicker;
    private TextField locationField;
    private Button submitButton;

    public AdminCourseAddingView() {
        createForm();
    }

    private void createForm() {
        add(new H1("Create a New Course!"));

        courseNameField = new TextField("Course Name");
        courseCodeField = new TextField("Course Code (e.g. COSC 101)");
        descriptionField = new TextArea("Description");
        termLabelField = new TextField("Term Label (e.g. 2025 Winter 2025)");
        creditsField = new TextField("Number of Credits");
        capacityField = new TextField("Capacity");
        startDatePicker = new DatePicker("Start Date");
        endDatePicker = new DatePicker("End Date");
        daysOfWeekComboBox = new ComboBox<>("Days of the Week");
        startTimePicker = new TimePicker("Start Time");
        endTimePicker = new TimePicker("End Time");
        locationField = new TextField("Location");

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

        daysOfWeekComboBox.setItems("Monday-Wednesday", "Tuesday-Thursday", "Wednesday-Friday", "Monday-Wednesday-Friday");

        HorizontalLayout datePickers = new HorizontalLayout(startDatePicker, endDatePicker);
        HorizontalLayout timePickers = new HorizontalLayout(startTimePicker, endTimePicker);

        submitButton = new Button("Create Course", event -> handleSubmit());

        add(courseNameField, courseCodeField, descriptionField, termLabelField, creditsField, capacityField, 
            datePickers, daysOfWeekComboBox, timePickers, locationField, submitButton);
    }

    private void handleSubmit() {
        if (fieldsAreEmpty()) {
            Notification.show("Please fill in all fields.", 3000, Notification.Position.TOP_CENTER);
            return;
        }

        Course course = new Course(
		    courseNameField.getValue(),
		    courseCodeField.getValue(),
		    Integer.parseInt(creditsField.getValue()),
		    descriptionField.getValue(),
		    Integer.parseInt(capacityField.getValue()),
		    locationField.getValue(),
		    LocalDate.parse(startDatePicker.getValue().toString()),
		    LocalDate.parse(endDatePicker.getValue().toString()),
		    termLabelField.getValue(),
		    daysOfWeekComboBox.getValue(),
		    LocalTime.parse(startTimePicker.getValue().toString()),
		    LocalTime.parse(endTimePicker.getValue().toString())
		);
		CourseDAO.addCourse(course);
    }

    private boolean fieldsAreEmpty() {
        return courseNameField.isEmpty() || courseCodeField.isEmpty() || descriptionField.isEmpty() ||
               termLabelField.isEmpty() || creditsField.isEmpty() || capacityField.isEmpty() ||
               startDatePicker.isEmpty() || endDatePicker.isEmpty() || daysOfWeekComboBox.isEmpty() ||
               startTimePicker.isEmpty() || endTimePicker.isEmpty() || locationField.isEmpty();
    }
}
