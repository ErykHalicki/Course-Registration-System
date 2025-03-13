package recordrangers.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;

@Route("404")  // This should be mapped to handle 404 errors
public class ErrorPage extends Div {
    public ErrorPage() {
    	
        setText("Oops! You do not have permission to access this page.");
        addClassName("error-page");
    }
}