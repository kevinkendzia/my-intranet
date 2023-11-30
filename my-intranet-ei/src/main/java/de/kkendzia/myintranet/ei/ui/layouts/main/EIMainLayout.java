package de.kkendzia.myintranet.ei.ui.layouts.main;

import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.server.auth.AccessAnnotationChecker;
import de.kkendzia.myintranet.ei._framework.view.AbstractEIView;
import de.kkendzia.myintranet.ei.core.session.EISession;
import de.kkendzia.myintranet.ei.ui.layouts.main.appbar.EIAppBar;
import de.kkendzia.myintranet.ei.ui.layouts.main.drawer.EIDrawer;
import de.kkendzia.myintranet.ei.ui.layouts.main.wrapper.EIViewWrapper;
import org.springframework.boot.info.BuildProperties;

import static java.util.Objects.requireNonNull;

/**
 * The main view is a top-level placeholder for other views.
 */
public class EIMainLayout
        extends AppLayout
{
    public EIMainLayout(
            EIMainLayoutPresenter presenter,
            EISession session,
            AccessAnnotationChecker accessChecker,
            BuildProperties buildProperties)
    {
        requireNonNull(presenter, "presenter can't be null!");
        requireNonNull(session, "session can't be null!");
        requireNonNull(accessChecker, "accessChecker can't be null!");

        addClassName("ei-main-layout");
        setPrimarySection(Section.DRAWER);
        addToDrawer(new EIDrawer(accessChecker, buildProperties));
        addToNavbar(new EIAppBar(presenter, session));
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
