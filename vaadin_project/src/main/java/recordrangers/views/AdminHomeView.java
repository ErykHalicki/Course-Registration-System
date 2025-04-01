package recordrangers.views;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.VaadinSession;

import recordrangers.models.Course;
import recordrangers.models.User;
import recordrangers.services.AdminCourseUses;
import recordrangers.services.Auth;

@Route("admin-home")
public class AdminHomeView extends AppLayout{

	User loggedInUser;


    private AdminCourseUses adminCourseUses;
    

    public AdminHomeView() {
    	
    	try {
    	adminCourseUses = new AdminCourseUses();
    	} catch (SQLException ex) {}
    	
    	loggedInUser = (User)VaadinSession.getCurrent().getAttribute("loggedInUser");
    	
    	try {
			if (loggedInUser == null || !Auth.isAdmin(loggedInUser.getUserId())) {
			    // Throwing a NotFoundException triggers the 404 error page
				UI.getCurrent().navigate("404");
				return;
			}
		} catch (SQLException e) {
			UI.getCurrent().navigate("404");
			e.printStackTrace();
			return;
		}
        createHeader();
        createDrawer();

        setContent(new Span("Welcome to the Admin Home Page"));
    }

    private void clearContent() {
        setContent(new Div());
    }

    private void createHeader() {
        // App Name on the left side of the header
        H1 appName = new H1("CampusNest");

        // User info on the right
        Span userInfo = new Span("Welcome !");
        if(loggedInUser != null) {
        	userInfo = new Span("Welcome " + loggedInUser.getFirstName() + "!");
        }
        // Create log out button
        Button logOutButton = new Button("Log Out", event -> {
        	VaadinSession.getCurrent().setAttribute("loggedInUser", null);
            getUI().ifPresent(ui -> ui.navigate("")); // Navigate to the login page when we log out
        });

        // Remove default background, border, and outline
        logOutButton.getStyle().set("background", "none");
        logOutButton.getStyle().set("border", "none");
        logOutButton.getStyle().set("outline", "none"); // Remove focus outline
        logOutButton.getStyle().set("box-shadow", "none"); // Remove shadow

        // Set the button's text color to match the link color
        logOutButton.getStyle().set("color", "var(--lumo-primary-text-color)"); // Use Vaadin's primary text color for consistency

        // Add margin to the button
        logOutButton.getStyle().set("margin-left", "10px");

        // Add hover effect to match link hover color
        logOutButton.getStyle().set("cursor", "pointer"); // Change cursor to pointer
        logOutButton.getStyle().set("text-decoration", "underline"); // Underline on hover

        // Add hover effects 
        logOutButton.getElement().executeJs(
            "this.style.transition = 'color 0.3s';" +
            "this.onmouseover = function() { this.style.color = 'var(--lumo-primary-color-50pct)'; };" + 
            "this.onmouseout = function() { this.style.color = 'var(--lumo-primary-text-color)'; };"
        );

        // Create a container for user info and logout button
        Div userContainer = new Div(userInfo, logOutButton);
        userContainer.getStyle().set("margin-left", "auto");

        // Create a HorizontalLayout for the header
        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), appName, userContainer);
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.setWidth("100%");
        header.expand(appName); // Expand the app name to push user info to the right

        // Add the header to the navbar
        addToNavbar(header);
    }

    public void createDrawer() {
        // Create tabs for the drawer
        Tab profile = new Tab(VaadinIcon.CLIPBOARD_USER.create(), new RouterLink("Profile", AdminProfileView.class));

        // Student search
        Tab studentSearch = new Tab(VaadinIcon.ACADEMY_CAP.create(), new RouterLink("Search for Students", AdminStudentSearchView.class));
        
        // Course search
        Tab courseSearch = new Tab(VaadinIcon.ACADEMY_CAP.create(), new RouterLink("Search or Modify a Courses", AdminCourseSearchView.class));

        // Create course
        /*
        Button addCourseButton = new Button("Add a Course", event -> showAddCourse());
        addCourseButton.getStyle().set("color", "grey");
        addCourseButton.getStyle().set("background", "none");
        addCourseButton.getStyle().set("border", "none");
        addCourseButton.getStyle().set("box-shadow", "none");
        addCourseButton.getStyle().set("padding", "0");
        */
        Tab addCourse = new Tab(VaadinIcon.PLUS_CIRCLE_O.create(), new RouterLink("Add a Course", AdminCourseAddingView.class));

        // View student grades
        Tab studentGrades = new Tab(VaadinIcon.DIPLOMA_SCROLL.create(), new RouterLink("Student Grades", AdminStudentReportsView.class));
        
        Tab manageStudents = new Tab(VaadinIcon.USERS.create(), new RouterLink("Manage Students", AdminManageStudentsView.class));


        Tabs tabs = new Tabs();
        tabs.add(profile);
        tabs.add(studentSearch);
        tabs.add(courseSearch);
        tabs.add(addCourse);
        tabs.add(studentGrades);
        tabs.add(manageStudents);
        tabs.setOrientation(Tabs.Orientation.VERTICAL);

        // Add the tabs to the drawer
        addToDrawer(tabs);
    }

    private void showAddCourse() {
        // Clear the content area, before adding new content
        clearContent();

        // Create a vertical layout for form inputs
        VerticalLayout formLayout = new VerticalLayout();
        formLayout.add(new H1("Create a New Course!"));

        // Course name
        TextField courseNameField = new TextField("Course Name");
        courseNameField.getStyle().set("width", "250px");
        courseNameField.setRequired(true);

        // Course code
        TextField courseCodeField = new TextField("Course Code (e.g. COSC 101)");
        courseCodeField.getStyle().set("width", "250px");
        courseCodeField.setRequired(true);

        // Description
        TextArea descriptionField = new TextArea("Description");
        descriptionField.getStyle().set("width", "350px");
        descriptionField.setRequired(true);

        // Term label
        TextField termLabelField = new TextField("Term Label (e.g. 2025 Winter 2025)");
        termLabelField.getStyle().set("width", "250px");
        termLabelField.setRequired(true);

        // Credits
        TextField creditsField = new TextField("Number of Credits");
        creditsField.setRequired(true);

        // Capacity
        TextField capacityField = new TextField("Capacity");
        capacityField.setRequired(true);

        // Dates
        DatePicker startDatePicker = new DatePicker("Start Date");
        startDatePicker.setRequired(true);

        DatePicker endDatePicker = new DatePicker("End Date");
        endDatePicker.setRequired(true);

        HorizontalLayout datePickers = new HorizontalLayout();
        datePickers.add(startDatePicker, endDatePicker);

        // Days of the week
        ComboBox<String> daysOfWeekComboBox = new ComboBox<>("Days of the Week");
        daysOfWeekComboBox.setItems("Monday-Wednesday", "Tuesday-Thursday", "Wednesday-Friday", "Monday-Wednesday-Friday");
        daysOfWeekComboBox.setRequired(true);

        // Times
        TimePicker startTimePicker = new TimePicker("Start Time");
        startTimePicker.setRequired(true);

        TimePicker endTimePicker = new TimePicker("End Time");
        endTimePicker.setRequired(true);

        HorizontalLayout timePickers = new HorizontalLayout();
        timePickers.add(startTimePicker, endTimePicker);

        // Location
        TextField locationField = new TextField("Location");
        locationField.setRequired(true);

        // Submit button
        Button submitButton;
            submitButton = new Button("Create Course", event -> {
                // Validate the form
                if (courseNameField.isEmpty()) {
                    Notification.show("Please fill in all fields.", 3000, Notification.Position.TOP_CENTER);
                } else if (courseCodeField.isEmpty()) {
                    Notification.show("Please fill in all fields.", 3000, Notification.Position.TOP_CENTER);
                } else if (descriptionField.isEmpty()) {
                    Notification.show("Please fill in all fields.", 3000, Notification.Position.TOP_CENTER);
                } else if (termLabelField.isEmpty()) {
                    Notification.show("Please fill in all fields.", 3000, Notification.Position.TOP_CENTER);
                } else if (creditsField.isEmpty()) {
                    Notification.show("Please fill in all fields.", 3000, Notification.Position.TOP_CENTER);
                } else if (startDatePicker.isEmpty()) {
                    Notification.show("Please fill in all fields.", 3000, Notification.Position.TOP_CENTER);
                } else if (endDatePicker.isEmpty()) {
                    Notification.show("Please fill in all fields.", 3000, Notification.Position.TOP_CENTER);
                } else if (daysOfWeekComboBox.isEmpty()) {
                    Notification.show("Please fill in all fields.", 3000, Notification.Position.TOP_CENTER);
                } else if (startTimePicker.isEmpty()) {
                    Notification.show("Please fill in all fields.", 3000, Notification.Position.TOP_CENTER);
                } else if (endTimePicker.isEmpty()) {
                    Notification.show("Please fill in all fields.", 3000, Notification.Position.TOP_CENTER);
                } else if (locationField.isEmpty()) {
                    Notification.show("Please fill in all fields.", 3000, Notification.Position.TOP_CENTER);
                } else {
                    // Get attributes
                    String courseName = courseNameField.getValue();
                    String courseCode = courseCodeField.getValue();
                    String description = descriptionField.getValue();
                    String termLabel = termLabelField.getValue();
                    int credits = Integer.parseInt(creditsField.getValue());
                    int capacity = Integer.parseInt(capacityField.getValue());
                    String startDate = startDatePicker.getValue().toString();
                    String endDate = endDatePicker.getValue().toString();
                    String daysOfWeek = daysOfWeekComboBox.getValue();
                    String startTime = startTimePicker.getValue().toString();
                    String endTime = endTimePicker.getValue().toString();
                    String location = locationField.getValue();
                    
                    try {
                        LocalDate startT = LocalDate.parse(startDate);
                        LocalDate endT = LocalDate.parse(endDate);
                        LocalTime startD = LocalTime.parse(startTime);
                        LocalTime endD = LocalTime.parse(endTime);
                        Course course = new Course(courseName, courseCode, credits, description, capacity, location, startT, endT, termLabel, daysOfWeek, startD, endD);
                        AdminCourseUses acu = new AdminCourseUses();
                        acu.addCourse(course);
                    } catch (SQLException ex) {
                        
                    }
                }
            });

        // Add components to the layout
        formLayout.add(courseNameField, courseCodeField, descriptionField, termLabelField, creditsField, capacityField, datePickers,
        daysOfWeekComboBox, timePickers, locationField, submitButton);
        setContent(formLayout);
    }
    
}
