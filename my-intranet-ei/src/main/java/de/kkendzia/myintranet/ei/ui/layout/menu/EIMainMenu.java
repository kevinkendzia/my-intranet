package de.kkendzia.myintranet.ei.ui.layout.menu;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.theme.lumo.LumoUtility;
import de.kkendzia.myintranet.ei.ui._framework.EIComponent;
import de.kkendzia.myintranet.ei.ui.layout.menu.MenuRouteAnalyzer.MenuRouteData;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment.STRETCH;
import static java.util.Objects.requireNonNull;

public class EIMainMenu
        extends Composite<VerticalLayout>
{
    private final Scroller scroller = new Scroller();

    // CONFIG
    private boolean rootGroupLabels = false;

    public EIMainMenu(
            MenuHeader header,
            MenuFooter footer)
    {
        VerticalLayout root = getContent();
        root.setAlignItems(STRETCH);
        root.setHeightFull();

        if (header != null)
        {
            root.add(header);
        }

        root.add(scroller);

        if (footer != null)
        {
            root.add(footer);
        }
    }

    @Override
    protected void onAttach(AttachEvent attachEvent)
    {
        super.onAttach(attachEvent);
        scroller.setContent(createNavigation());
    }

    private Component createNavigation()
    {
        MenuRouteConfiguration config = MenuRouteConfiguration.forSessionScope();

        VerticalLayout vlNav = new VerticalLayout();
        vlNav.setPadding(false);
        vlNav.setSpacing(true);

        List<MenuRouteData> routes = config.getMenuRoutes();

        if(this.rootGroupLabels)
        {
            routes.forEach(x -> {
                createSingleNavigation(x)
            });
        }

        for (MenuRouteData route : routes)
        {
            SideNavItem itm = new SideNavItem(
                    getTranslation(route.label()),
                    route.navigationTarget(),
                    route.iconSupplier().get());
            nav.addItem(itm);
        }

        return vlNav;
    }

    private Component createSingleNavigation(String label, boolean collapsible, Collection<MenuRouteData> routes)
    {
        SideNav nav = new SideNav(label);
        nav.setCollapsible(collapsible);
        for (MenuRouteData route : routes)
        {
            SideNavItem itm = createNavItemsRecursive(route);
            nav.addItem(itm);
        }

        return nav;
    }

    private SideNavItem createNavItemsRecursive(MenuRouteData route)
    {
        SideNavItem itm = createSubNavItem(route);

        route.subRoutes().forEach(r -> {
            SideNavItem sub = createSubNavItem(r);
            itm.addItem(sub);
        });

        return itm;
    }

    private SideNavItem createSubNavItem(MenuRouteData route)
    {

        return new SideNavItem(
                getTranslation(route.label()),
                route.navigationTarget(),
                route.iconSupplier().get());
    }


    /*
     * SETTER / GETTER
     */

    public void setRootGroupLabels(boolean rootGroupLabels)
    {
        this.rootGroupLabels = rootGroupLabels;
    }

    /*
     * TYPES
     */

    public static class MenuRouteConfiguration
    {
        private final List<MenuRouteData> menuRoutes;
        private final List<MenuTreeData> menuTree;

        public MenuRouteConfiguration(List<MenuRouteData> availableMenuRoutes)
        {
            this.menuRoutes = requireNonNull(availableMenuRoutes, "availableMenuRoutes can't be null!");
            menuTree =
        }

        public List<MenuRouteData> getMenuRoutes()
        {
            return menuRoutes;
        }

        public static MenuRouteConfiguration forSessionScope()
        {
            MenuRouteAnalyzer analyzer = MenuRouteAnalyzer.getInstance();
            List<MenuRouteData> routes = RouteConfiguration
                    .forSessionScope()
                    .getAvailableRoutes()
                    .stream()
                    .map(route -> analyzer.analyze(route).orElse(null))
                    .filter(Objects::nonNull)
                    .toList();

            return new MenuRouteConfiguration(routes);
        }

        public static class MenuTreeData
        {

        }
    }

    public static class MenuHeader extends EIComponent<Header>
    {
        public MenuHeader()
        {
            H1 appName = new H1("My Intranet");
            appName.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);

            Header header = getContent();
            header.add(appName);
        }
    }

    public static class MenuFooter extends EIComponent<Footer>
    {

    }
}
