package recordrangers.views;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

@Route
public class AdminHomeView extends VerticalLayout{

    public AdminHomeView(){
        AdminHeaderComponent header = new AdminHeaderComponent("Admin User");
        add(header);

        // Welcome span
        add(new Span("Welcome to the admin home page!"));

        // Menu layout
        VerticalLayout menuLayout = new VerticalLayout();
        menuLayout.setSpacing(true);
        menuLayout.setPadding(true);

        // Add menu options (router links)
        RouterLink searchStudentsLink = new RouterLink("Search for students", LoginView.class);
        RouterLink searchCoursesLink = new RouterLink("Search for courses", LoginView.class);
        RouterLink addCourseLink = new RouterLink("Create a course", LoginView.class);
        RouterLink removeCourseLink = new RouterLink("Remove a course", LoginView.class);

        menuLayout.add(searchStudentsLink, searchCoursesLink, addCourseLink, removeCourseLink);

        add(menuLayout);
    }
}
