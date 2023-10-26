package de.kkendzia.myintranet.ei.ui.layouts.main;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.auth.AccessAnnotationChecker;
import com.vaadin.flow.spring.security.AuthenticationContext;
import com.vaadin.flow.theme.lumo.LumoUtility;
import com.vaadin.flow.theme.lumo.LumoUtility.Overflow;
import de.kkendzia.myintranet.ei.core.view.AbstractEIView;
import de.kkendzia.myintranet.ei.core.view.EIView;
import de.kkendzia.myintranet.ei.core.view.mixins.HasSidebarConfig;
import de.kkendzia.myintranet.ei.ui.components.sidebar.ConfigurableSidebarNotifier;
import de.kkendzia.myintranet.ei.ui.components.sidebar.ConfigurableSidebar;
import de.kkendzia.myintranet.ei.core.view.mixins.HasToolbarConfig;
import de.kkendzia.myintranet.ei.ui.components.toolbar.ConfigurableToolbarNotifier;
import de.kkendzia.myintranet.ei.ui.components.toolbar.ConfigurableToolbar;
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
        setContent(new ViewWrapper(eiView));
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

    public static class ViewWrapper extends Composite<HorizontalLayout>
    {
        public ViewWrapper(EIView view)
        {
            view.addClassName(LumoUtility.Overflow.AUTO);

            VerticalLayout vlContent = new VerticalLayout();
            vlContent.addClassNames("ei-main-toolbar-layout");
            vlContent.setSizeFull();
            vlContent.setPadding(false);
            vlContent.setAlignItems(FlexComponent.Alignment.STRETCH);

            if(view instanceof HasToolbarConfig t)
            {
                t.getOptionalToolbarConfigSupplier().ifPresent(tb ->
                {
                    ConfigurableToolbar toolbar = new ConfigurableToolbar(tb);
                    vlContent.add(toolbar);

                    if(view instanceof ConfigurableToolbarNotifier n)
                    {
                        n.addToolbarChangeListener(toolbar);
                    }
                });
            }
            vlContent.addAndExpand((Component) view);

            HorizontalLayout root = getContent();
            root.addClassNames("ei-main-sidebar-layout");
            root.addClassNames(Overflow.AUTO);
            root.setSizeFull();
            root.setPadding(true);
            root.setAlignItems(FlexComponent.Alignment.STRETCH);

            if(view instanceof HasSidebarConfig s)
            {
                s.getOptionalSidebarConfigSupplier().ifPresent(sb ->
                {
                    ConfigurableSidebar sidebar = new ConfigurableSidebar(sb);
                    root.add(sidebar);

                    if(view instanceof ConfigurableSidebarNotifier n)
                    {
                        n.addSidebarChangeListener(sidebar);
                    }
                });
            }

            root.addAndExpand(vlContent);
        }
    }
}
