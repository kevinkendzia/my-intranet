package de.kkendzia.myintranet.ei.ui.views.login;

import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import de.kkendzia.myintranet.ei.core.view.AbstractEIView;

@Route(LoginView.NAV)
@AnonymousAllowed
public class LoginView extends AbstractEIView<VerticalLayout>
{
    public static final String NAV = "login";
    private final LoginForm login = new LoginForm();

    public LoginView()
    {
        login.setAction("login");

        VerticalLayout root = getContent();
        root.setAlignItems(Alignment.CENTER);
        root.setJustifyContentMode(JustifyContentMode.CENTER);
        root.add(login);
    }

    @Override
    protected void beforeEnterView(BeforeEnterEvent event)
    {
        if (event.getLocation()
                .getQueryParameters()
                .getParameters()
                .containsKey("error"))
        {
            login.setError(true);
        }
    }
}
