package de.kkendzia.myintranet.ei.ui.layouts.main;

import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.server.auth.AccessAnnotationChecker;
import com.vaadin.flow.spring.security.AuthenticationContext;
import de.kkendzia.myintranet.ei.core.view.AbstractEIView;

import static java.util.Objects.requireNonNull;

/**
 * The main view is a top-level placeholder for other views.
 */
public class EIMainLayout
        extends AppLayout
{
    public EIMainLayout(
            EIMainLayoutPresenter presenter,
            AuthenticationContext authContext,
            AccessAnnotationChecker accessChecker)
    {
        requireNonNull(presenter, "presenter can't be null!");
        requireNonNull(authContext, "authContext can't be null!");
        requireNonNull(accessChecker, "accessChecker can't be null!");

        addClassName("ei-main-layout");
        setPrimarySection(Section.DRAWER);
        addToDrawer(new EIDrawer(accessChecker));
        addToNavbar(new EIAppBar(presenter, authContext));
    }

    @Override
    public void showRouterLayoutContent(HasElement content)
    {
        AbstractEIView<?> eiView = requireEIView(content);
        setContent(new EIViewWrapper(eiView));
        afterNavigation();
    }

    private static AbstractEIView<?> requireEIView(HasElement content)
    {
        if (content instanceof AbstractEIView<?> eiView)
        {
            return eiView;
        }

        throw new IllegalArgumentException("MainLayout content must be a EIView! " + content.getClass());
    }
}
