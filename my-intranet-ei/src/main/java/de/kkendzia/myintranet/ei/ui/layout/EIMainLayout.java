package de.kkendzia.myintranet.ei.ui.layout;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import de.kkendzia.myintranet.ei.core.view.AbstractEIView;
import de.kkendzia.myintranet.ei.core.view.sidebar.ViewSidebar;
import de.kkendzia.myintranet.ei.core.view.toolbar.ViewToolbar;
import de.kkendzia.myintranet.ei.ui.components.menu.DrawerMenu;
import de.kkendzia.myintranet.ei.ui.components.menu.provider.AnnotationItemProvider;
import de.kkendzia.myintranet.ei.ui.layout.appbar.EIAppBar;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * The main view is a top-level placeholder for other views.
 */
public class EIMainLayout
        extends AppLayout
{
    private EIMainLayoutPresenter presenter;

    @Autowired
    public EIMainLayout(EIMainLayoutPresenter presenter)
    {
        this.presenter = presenter;

        DrawerMenu menu =
                new DrawerMenu(
                        new DrawerMenu.MenuHeader(),
                        new DrawerMenu.MenuFooter());
        menu.setItemProvider(
                new AnnotationItemProvider(
                        List.of(
                                new DrawerMenu.DrawerMenuItem(
                                        "nav",
                                        "menu.target.nav"),
                                new DrawerMenu.DrawerMenuItem(
                                        "ah",
                                        "menu.target.ah"),
                                new DrawerMenu.DrawerMenuItem(
                                        "other",
                                        "menu.target.other"))));

        setPrimarySection(Section.DRAWER);
        addToDrawer(menu);
        addToNavbar(new EIAppBar(presenter));
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

        throw new IllegalArgumentException("MainLayout content must be a EIView!");
    }

    public static class ViewWrapper extends Composite<HorizontalLayout>
    {
        public ViewWrapper(AbstractEIView<?> view)
        {
            VerticalLayout vlContent = new VerticalLayout();
            vlContent.setAlignItems(FlexComponent.Alignment.STRETCH);
            view.getOptionalToolbarConfig().ifPresent(tb -> vlContent.add(new ViewToolbar(tb)));
            vlContent.addAndExpand(view);

            HorizontalLayout root = getContent();
            root.setAlignItems(FlexComponent.Alignment.STRETCH);
            view.getOptionalSidebarConfig().ifPresent(sb -> root.add(new ViewSidebar(sb)));
            root.addAndExpand(view);
        }
    }
}
