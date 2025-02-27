package recordrangers.views;

import java.sql.SQLException;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.Route;

import recordrangers.models.User;
import recordrangers.models.Auth;

@Route
public class LoginView extends Composite<LoginOverlay> {
    
    public LoginView() {
        // create login form
        LoginOverlay loginOverlay = getContent();
        loginOverlay.setTitle("CampusNest");
        String desc = "Manage your academics! Students can preform all their needed actions for managing their academic careers. " +
        "Admin users can create and manage courses, as well manage students.";
        loginOverlay.setDescription(desc);
        loginOverlay.setOpened(true);

        Auth auth = new Auth();

        // add action listeners
        loginOverlay.addLoginListener(event -> {
            String username = event.getUsername();
            String password = event.getPassword();
            // pass data to auth sign in method, if no match create notification

            User user;
            try {
                user = auth.signIn(username, password);
                // check if signIn returns null, if so send notification
                if (user == null) {
                    Notification notification = new Notification();
                    notification.show("Sign in failed. Invalid username or password!");
                } else {
                    // route to either student home page or admin home page
                    if (auth.isStudent(user.getUserId())) {
                        // route to student home page
                    } else if (auth.isAdmin(user.getUserId())) {
                        // route to admin home page
                    } else {
                        Notification notification = new Notification();
                        notification.show("Sign in failed! ");
                    }

                }
                } catch (SQLException ex) {
            }

        });
    }   
}
