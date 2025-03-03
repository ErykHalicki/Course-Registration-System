package recordrangers.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.RouterLink;

public class AdminHeaderComponent extends HorizontalLayout {

    public AdminHeaderComponent(String username) {
        // App Name on the left
        H1 appName = new H1("CampusNest");

        // User info on the right
        Span userInfo = new Span(username + " (Admin)");
        userInfo.getStyle().set("margin-right", "10px");
        RouterLink profileLink = new RouterLink("Profile", ProfileView.class);


        // Create log out button
        Button logOutButton = new Button("Log Out", event -> {
            getUI().ifPresent(ui -> ui.navigate("LoginView"));
        });
        logOutButton.getStyle().set("background", "none");
        logOutButton.getStyle().set("margin-left", "10px");

        // Create a container for user info and profile link
        Div userContainer = new Div(userInfo, profileLink, logOutButton);
        userContainer.getStyle().set("margin-left", "auto");
        

        // Add components to the header
        add(appName, userContainer);

        // Align items vertically in the center
        setAlignItems(FlexComponent.Alignment.CENTER);

        // Set width to 100%
        setWidth("100%");
    }
}
