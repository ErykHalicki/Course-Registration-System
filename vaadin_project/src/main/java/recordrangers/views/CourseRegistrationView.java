package recordrangers.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import recordrangers.models.Course;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@PageTitle("Course Registration")
@Route(value = "register", layout = MainLayout.class)
public class CourseRegistrationView extends VerticalLayout {

    private List<Course> courses;
    private Grid<Course> courseGrid;

    // UI components for filtering
    private ComboBox<String> criteriaComboBox;
    private ComboBox<String> levelComboBox;
    private TextField subjectField;
    private TextField creditsField;
    private Button searchButton;
    private Button resetButton;

    public CourseRegistrationView() {
        // Initialize the criteria selection ComboBox.
        criteriaComboBox = new ComboBox<>("Search Criteria");
        criteriaComboBox.setItems("Course Level", "Course Subject", "Number of Credits");
        criteriaComboBox.setPlaceholder("Select search criteria");

        // Initialize input fields for each search criterion.
        levelComboBox = new ComboBox<>("Course Level");
        levelComboBox.setItems("100", "200", "300", "400");
        levelComboBox.setPlaceholder("Select Level");
        levelComboBox.setVisible(false);

        subjectField = new TextField("Course Subject");
        subjectField.setPlaceholder("Enter subject");
        subjectField.setVisible(false);
        
        creditsField = new TextField("Number of Credits");
        creditsField.setPlaceholder("Enter credits");
        creditsField.setVisible(false);

        searchButton = new Button("Search");
        resetButton = new Button("Reset");

        // Arrange the search components in a horizontal layout.
        HorizontalLayout searchLayout = new HorizontalLayout(
            criteriaComboBox, levelComboBox, subjectField, creditsField, searchButton, resetButton
        );
        add(searchLayout);

        // Show the corresponding input field based on the selected criterion.
        criteriaComboBox.addValueChangeListener(event -> {
            String selected = event.getValue();
            // Hide all inputs initially.
            levelComboBox.setVisible(false);
            subjectField.setVisible(false);
            creditsField.setVisible(false);
            if ("Course Level".equals(selected)) {
                levelComboBox.setVisible(true);
            } else if ("Course Subject".equals(selected)) {
                subjectField.setVisible(true);
            } else if ("Number of Credits".equals(selected)) {
                creditsField.setVisible(true);
            }
        });

        // Set up the grid (use single selection mode).
        courseGrid = new Grid<>(Course.class);
        courseGrid.removeAllColumns();
        courseGrid.setSelectionMode(Grid.SelectionMode.SINGLE); // Single selection
        // Columns
        courseGrid.addColumn(Course::getCourseName).setHeader("Course Code");
        courseGrid.addColumn(Course::getMaxCapacity).setHeader("Max Capacity");
        courseGrid.addColumn(Course::getEnrollment).setHeader("Enrollment");
        courseGrid.addColumn(Course::getNumCredits).setHeader("Credits");

        // Initialize the courses list with example data.
        courses = new ArrayList<>();
        courses.add(new Course("MATH101", 30, 25));
        courses.add(new Course("PHYS201", 40, 38));
        courses.add(new Course("COSC305", 50, 45));

        courseGrid.setItems(courses);
        add(courseGrid);

        // Add a Register button that uses the selected course from the grid.
        Button registerButton = new Button("Register");
        registerButton.addClickListener(e -> {
            // Get the currently selected course
            Course selectedCourse = courseGrid.asSingleSelect().getValue();
            if (selectedCourse == null) {
                Notification.show("Please select a course first.");
                return;
            }

            // Check if there's room in the course
            if (selectedCourse.getEnrollment() < selectedCourse.getMaxCapacity()) {
                selectedCourse.setEnrollment(selectedCourse.getEnrollment() + 1);
                // Optionally, update the list of enrolled students in the Course object as well
                // selectedCourse.getEnrolledStudents().add(new Student(...));

                // Refresh the item in the grid to display updated enrollment
                courseGrid.getDataProvider().refreshItem(selectedCourse);

                Notification.show("Successfully registered for " + selectedCourse.getCourseName());
            } else {
                Notification.show("Course is full. Unable to register.");
            }
        });

        // Add the Register button to the layout
        add(registerButton);

        // Wire up the Search button.
        searchButton.addClickListener(e -> {
            String selectedCriteria = criteriaComboBox.getValue();
            String level = "";
            String subject = "";
            String credits = "";
            if ("Course Level".equals(selectedCriteria)) {
                level = (levelComboBox.getValue() != null) ? levelComboBox.getValue() : "";
            } else if ("Course Subject".equals(selectedCriteria)) {
                subject = subjectField.getValue();
            } else if ("Number of Credits".equals(selectedCriteria)) {
                credits = creditsField.getValue();
            }
            List<Course> filteredCourses = filterCourses(level, subject, credits);
            courseGrid.setItems(filteredCourses);
        });

        // Wire up the Reset button.
        resetButton.addClickListener(e -> {
            criteriaComboBox.clear();
            levelComboBox.clear();
            subjectField.clear();
            creditsField.clear();
            levelComboBox.setVisible(false);
            subjectField.setVisible(false);
            creditsField.setVisible(false);
            courseGrid.setItems(courses);
        });
    }

    /**
     * Filters the courses list based on provided criteria.
     *
     * @param level   Selected course level (e.g., "100"). If empty, no filtering by level is applied.
     * @param subject Course subject text. If empty, no filtering by subject is applied.
     * @param credits Number of credits as a string. If empty, no filtering by credits is applied.
     * @return A list of courses matching the criteria.
     */
    public List<Course> filterCourses(String level, String subject, String credits) {
        Stream<Course> stream = courses.stream();

        // Filter by Course Level if provided.
        if (level != null && !level.isEmpty()) {
            stream = stream.filter(course -> {
                String digits = course.getCourseName().replaceAll("\\D", "");
                if (digits.isEmpty()) {
                    return false;
                }
                return digits.charAt(0) == level.charAt(0);
            });
        }

        // Filter by Course Subject if provided.
        if (subject != null && !subject.isEmpty()) {
            String subjectQuery = subject.toLowerCase();
            stream = stream.filter(course -> course.getCourseName().toLowerCase().contains(subjectQuery));
        }

        // Filter by Number of Credits if provided.
        if (credits != null && !credits.isEmpty()) {
            try {
                int c = Integer.parseInt(credits);
                stream = stream.filter(course -> course.getNumCredits() == c);
            } catch (NumberFormatException ex) {
                // You could log or notify the user about invalid input
            }
        }

        return stream.collect(Collectors.toList());
    }
}
