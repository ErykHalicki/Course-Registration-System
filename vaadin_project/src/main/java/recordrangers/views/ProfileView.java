package recordrangers.views;

import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Profile")
@Route(value = "profile", layout = MainLayout.class)
public class ProfileView extends VerticalLayout {

    public ProfileView() {
        add(new Paragraph("Student Name: John Doe"));
        add(new Paragraph("Email: johndoe@example.com"));
        add(new Paragraph("Enrolled Since: 2023"));
    }
}