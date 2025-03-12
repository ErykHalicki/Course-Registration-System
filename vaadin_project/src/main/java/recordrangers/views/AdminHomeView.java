package recordrangers.views;

import java.sql.SQLException;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.NotFoundException;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.VaadinSession;
import recordrangers.models.User;
import recordrangers.services.Auth;

@Route("admin-home")
public class AdminHomeView extends AppLayout{
	User loggedInUser;
    public AdminHomeView() {
    	
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
        Tabs tabs = new Tabs(
            new Tab(VaadinIcon.CLIPBOARD_USER.create(), new RouterLink("Profile", AdminHomeView.class)),
            new Tab(VaadinIcon.USER.create(), new RouterLink("Search for Students", AdminHomeView.class)),
            new Tab(VaadinIcon.ACADEMY_CAP.create(), new RouterLink("Search for Courses", AdminHomeView.class)),
            new Tab(VaadinIcon.PLUS_CIRCLE_O.create(), new RouterLink("Add a Course", AdminHomeView.class)),
            new Tab(VaadinIcon.MINUS_CIRCLE.create(), new RouterLink("Delete a Course", AdminHomeView.class))
        );
        tabs.setOrientation(Tabs.Orientation.VERTICAL);

        // Add the tabs to the drawer
        addToDrawer(tabs);
    }
}
