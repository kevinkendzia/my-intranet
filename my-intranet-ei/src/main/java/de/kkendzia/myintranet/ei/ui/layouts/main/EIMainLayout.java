package de.kkendzia.myintranet.ei.ui.layouts.main;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.server.auth.AccessAnnotationChecker;
import com.vaadin.flow.spring.security.AuthenticationContext;
import de.kkendzia.myintranet.ei.core.view.AbstractEIView;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * The main view is a top-level placeholder for other views.
 */
public class EIMainLayout
        extends AppLayout
{
    private EIMainLayoutPresenter presenter;

    @Autowired
    public EIMainLayout(AccessAnnotationChecker checker, EIMainLayoutPresenter presenter, AuthenticationContext authContext)
    {
        this.presenter = presenter;

        addClassName("ei-main-layout");
        setPrimarySection(Section.DRAWER);
        addToDrawer(new EIDrawer(checker));
        addToNavbar(new EIAppBar(presenter, authContext));
    }

    @Override
    public void showRouterLayoutContent(HasElement content)
    {
        AbstractEIView<?> eiView = requireEIView(content);
        setContent(new EIViewWrapper(eiView));
        afterNavigation();
    }

    private static Component requireComponent(HasElement content)
    {
        if (content != null)
        {
            return content
                    .getElement()
                    .getComponent()
                    .orElseThrow(() -> new IllegalArgumentException(
                            "MainLayout content must be a Component"));
        }
        return null;
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
