package de.kkendzia.myintranet.ei.ui.layout.menu;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.router.RouteData;
import com.vaadin.flow.theme.lumo.LumoUtility;
import de.kkendzia.myintranet.ei.ui.layout.menu.MenuRouteAnalyzer.MenuRouteData;

import java.util.List;
import java.util.Optional;

import static com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment.STRETCH;

public class EIMainMenu
        extends Composite<VerticalLayout>
{
    public EIMainMenu()
    {
        H1 appName = new H1("My Intranet");
        appName.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);
        Header header = new Header(appName);

        VerticalLayout root = getContent();
        root.setAlignItems(STRETCH);
        root.setHeightFull();

        root.add(header);
        root.add(new Scroller(createNavigation()));
        root.add(new Footer());
    }

    private SideNav createNavigation()
    {
        SideNav nav = new SideNav();

        MenuRouteAnalyzer analyzer = MenuRouteAnalyzer.getInstance();
        List<RouteData> routes = RouteConfiguration.forSessionScope().getAvailableRoutes();
        for (RouteData route : routes)
        {
            Optional<MenuRouteData> menuRoute = analyzer.analyze(route);
            if (menuRoute.isPresent())
            {
                MenuRouteData menuRouteData = menuRoute.get();
                SideNavItem itm = new SideNavItem(
                        getTranslation(menuRouteData.label()),
                        route.getNavigationTarget(),
                        menuRouteData.iconSupplier().get());
                nav.addItem(itm);
            }
        }

        return nav;
    }
}
