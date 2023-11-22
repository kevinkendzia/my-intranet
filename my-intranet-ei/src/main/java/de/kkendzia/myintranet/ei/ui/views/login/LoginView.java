package de.kkendzia.myintranet.ei.ui.views.login;

import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import de.kkendzia.myintranet.ei.core.parameters.ParameterDefinition;
import de.kkendzia.myintranet.ei.core.view.AbstractEIView;

import static de.kkendzia.myintranet.ei.core.parameters.ParameterDefinition.booleanParam;
import static de.kkendzia.myintranet.ei.core.parameters.ParameterDefinition.stringParam;

@Route(LoginView.NAV)
@AnonymousAllowed
public class LoginView extends AbstractEIView<VerticalLayout>
{
    public static final String NAV = "login";
    private static final ParameterDefinition<Boolean> PARAM_ERROR = booleanParam("error");
    private static final ParameterDefinition<String> PARAM_USERNAME = stringParam("userName");

    private final LoginForm login = new LoginForm();

    public LoginView()
    {
        registerQueryParameter(PARAM_ERROR);
        registerQueryParameter(PARAM_USERNAME);

        login.setForgotPasswordButtonVisible(false);
        login.setAction(NAV);

        VerticalLayout root = getContent();
        root.setSizeFull();
        root.setAlignItems(Alignment.CENTER);
        root.setJustifyContentMode(JustifyContentMode.CENTER);
        root.add(login);
    }

    @Override
    protected void beforeEnterView(BeforeEnterEvent event)
    {
        qpValues(PARAM_USERNAME).findAny().ifPresent(username ->
        {
            // Cookbook - Hack: https://cookbook.vaadin.com/login-form-initialize-credentials
            // For PW: this.$.vaadinLoginPassword.value
            login.getElement().executeJs("this.$.vaadinLoginUsername.value = $0;", username);
        });
        qpValues(PARAM_ERROR).findAny().ifPresent(login::setError);
    }
}
