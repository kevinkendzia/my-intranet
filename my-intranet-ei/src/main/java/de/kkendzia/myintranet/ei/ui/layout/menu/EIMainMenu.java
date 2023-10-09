package de.kkendzia.myintranet.ei.ui.layout.menu;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.theme.lumo.LumoUtility;
import de.kkendzia.myintranet.ei.ui.views.about.AboutView;
import de.kkendzia.myintranet.ei.ui.views.helloworld.HelloWorldView;
import org.vaadin.lineawesome.LineAwesomeIcon;

public class EIMainMenu extends Composite<VerticalLayout>
{
    public EIMainMenu()
    {
        H1 appName = new H1("My Intranet");
        appName.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);
        Header header = new Header(appName);

        VerticalLayout root = getContent();
        root.add(header);
        root.add(new Scroller(createNavigation()));
        root.add(footer);
    }

    private SideNav createNavigation() {
        SideNav nav = new SideNav();

        nav.addItem(new SideNavItem("Hello World", HelloWorldView.class, LineAwesomeIcon.GLOBE_SOLID.create()));
        nav.addItem(new SideNavItem("About", AboutView.class, LineAwesomeIcon.FILE.create()));

        return nav;
    }

    private Footer createFooter() {
        Footer layout = new Footer();

        return layout;
    }
}
