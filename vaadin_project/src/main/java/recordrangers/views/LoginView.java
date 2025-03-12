package recordrangers.views;

import java.sql.SQLException;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

import recordrangers.models.User;
import recordrangers.services.Auth;


@Route("")
public class LoginView extends Composite<LoginOverlay> {
    
    public LoginView() {
        // create login form
        LoginOverlay loginOverlay = getContent();
        loginOverlay.setTitle("CampusNest");
        String desc = "Manage your academics! Students can preform all their needed actions for managing their academic careers. " +
        "Admin users can create and manage courses, as well manage students.";
        loginOverlay.setDescription(desc);
        loginOverlay.setOpened(true);
        
        LoginI18n i18n = LoginI18n.createDefault();
        i18n.setAdditionalInformation("Don't have an account? Visit /create-account to sign up.");
        loginOverlay.setI18n(i18n);
    

        Auth auth = new Auth();

        // add action listeners
        loginOverlay.addLoginListener(event -> {
            String username = event.getUsername();
            String password = event.getPassword();
            // pass data to auth sign in method, if no match create notification

            User user;
            try {
                user = auth.signIn(username, password);
                Notification.show("got user: " + user.getUserId());
                // check if signIn returns null, if so send notification
                if (user == null) {
                    Notification.show("Sign in failed. Invalid username or password!");
                } else {
                    // route to either student home page or admin home page
                	VaadinSession.getCurrent().setAttribute("loggedInUser", user);
                	
                    if (auth.isStudent(user.getUserId())) {
                        // route to student home page     	
                    	getUI().ifPresent(ui -> ui.navigate("student-home"));
                    } else if (auth.isAdmin(user.getUserId())) {
                        // route to admin home page
                    	getUI().ifPresent(ui -> ui.navigate("admin-home"));
                    } else {
                        Notification.show("Sign in failed! ");
                    }

                }
                } catch (SQLException ex) {
                	Notification.show("SQL Error: " + ex.getMessage());
                	ex.printStackTrace();
            }

        });
    }
 
}
