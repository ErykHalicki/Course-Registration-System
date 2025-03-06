package recordrangers.views;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

@Route("")
public class MainLayout extends AppLayout {

    public MainLayout() {
        // Top Bar
        H1 title = new H1("Course Registration System");
        title.getStyle().set("margin", "0 auto");

        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), title);
        header.setWidthFull();
        header.setPadding(true);
        header.setAlignItems(Alignment.CENTER);

        addToNavbar(header);

        VerticalLayout menu = new VerticalLayout(
                new RouterLink("Course Registration", CourseRegistrationView.class),
                new RouterLink("Profile", ProfileView.class),
                new RouterLink("Current Courses", CurrentCoursesView.class),
                new RouterLink("Course Search", CourseSearchView.class)
        );

        addToDrawer(menu);
    }
}