package recordrangers.views;

import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;

import recordrangers.models.Enrollment;
import recordrangers.models.StudentGradeReport;
import recordrangers.services.StudentGrades;



@PageTitle("Student Grades")
@Route(value = "admin-student-reports", layout = AdminHomeView.class)
public class AdminStudentReportsView extends VerticalLayout {

    private final StudentGrades gradesService;
    private TextField searchField;
    private Grid<Enrollment> enrollmentGrid;
    private StudentGradeReport currentReport;
    private Button exportButton;
    private H3 studentInfoHeader;
    
    public AdminStudentReportsView(StudentGrades gradesService) {
        this.gradesService = gradesService;
        
        setSizeFull();
        add(new H1("Student Grade Report"));
        
        createSearchLayout();
        createStudentInfoHeader();
        createEnrollmentGrid();
        createExportButton();
    }
    
    public void createSearchLayout() {
        HorizontalLayout searchLayout = new HorizontalLayout();
        searchField = new TextField("Search by student name or ID");
        searchField.setPlaceholder("first name last name or studentId");
        searchField.getStyle().set("width", "400px");

        Button searchButton = new Button("Search", VaadinIcon.SEARCH.create());
        searchButton.addClickListener(e -> searchStudentGrades());

        searchLayout.add(searchField, searchButton);
        searchLayout.setDefaultVerticalComponentAlignment(Alignment.END);
        add(searchLayout);
    }

    public void createStudentInfoHeader() {
        studentInfoHeader = new H3();
        studentInfoHeader.setVisible(false);
        add(studentInfoHeader);
    }

    public void createEnrollmentGrid() {
        enrollmentGrid = new Grid<>(Enrollment.class, false);
        enrollmentGrid.addColumn(Enrollment::getCourseCode).setHeader("Course Code").setSortable(true);
        enrollmentGrid.addColumn(Enrollment::getCourseName).setHeader("Course Name");
        enrollmentGrid.addColumn(Enrollment::getCredits).setHeader("Credits");
        enrollmentGrid.addColumn(Enrollment::getGrade).setHeader("Grade").setSortable(true);
        enrollmentGrid.addColumn(Enrollment::getStatus).setHeader("Status");
        
        enrollmentGrid.setHeight("400px");
        enrollmentGrid.setVisible(false);
        add(enrollmentGrid);
    }

    public void createExportButton() {
        exportButton = new Button("Export to CSV", VaadinIcon.DOWNLOAD.create());
        exportButton.setEnabled(false);
        exportButton.addClickListener(e -> exportToCSV());
        exportButton.setVisible(false);
        add(exportButton);
    }

    public void searchStudentGrades() {
        String searchTerm = searchField.getValue().trim();
        if(searchField.isEmpty()) {
            Notification.show("Search field cannot be empty!", 3000, Notification.Position.TOP_CENTER);
            return;
        }

        try {
            currentReport = gradesService.getStudentGrades(searchTerm);
            
            if(currentReport != null) {
                studentInfoHeader.setText("Student: " + currentReport.getFirstName() + " " +
                                          currentReport.getLastName() + 
                                          " (ID: " + currentReport.getStudentId() + ")");
                studentInfoHeader.setVisible(true);

                enrollmentGrid.setItems(currentReport.getEnrollments());
                enrollmentGrid.setVisible(true);

                exportButton.setEnabled(!currentReport.getEnrollments().isEmpty());
                exportButton.setVisible(true);
            } else {
                studentInfoHeader.setVisible(false);
                enrollmentGrid.setVisible(false);
                exportButton.setVisible(false);
                Notification.show("No results found, please try again.", 3000, Notification.Position.TOP_CENTER);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void exportToCSV() {
        if(currentReport == null) {
            return;
        }

        String csvContent = gradesService.generateCSVReport(currentReport);
        StreamResource resource = new StreamResource("student_" + currentReport.getStudentId() + " _grades.csv", 
        () -> new java.io.ByteArrayInputStream(csvContent.getBytes(StandardCharsets.UTF_8)));

        Anchor downloadLink = new Anchor(resource, "Download CSV");
        downloadLink.getElement().setAttribute("download", true);
        downloadLink.add(new Button(VaadinIcon.DOWNLOAD.create()));
        
        add(downloadLink);
    }
}
