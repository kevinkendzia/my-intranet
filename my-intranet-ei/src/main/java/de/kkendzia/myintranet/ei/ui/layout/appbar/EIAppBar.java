package de.kkendzia.myintranet.ei.ui.layout.appbar;

import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.theme.lumo.LumoUtility.FontSize;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import de.kkendzia.myintranet.ei.ui._framework.EIComponent;

public class EIAppBar extends EIComponent<HorizontalLayout> implements AfterNavigationObserver
{
    private final H2 viewTitle = new H2();

    public EIAppBar()
    {
        DrawerToggle toggle = new DrawerToggle();
        toggle.setAriaLabel("Menu toggle");

        viewTitle.addClassNames(FontSize.LARGE, Margin.NONE);

        HorizontalLayout root = getContent();
        root.add(toggle);
        root.add(viewTitle);
    }

    @Override
    public void afterNavigation(AfterNavigationEvent afterNavigationEvent)
    {
        viewTitle.setText(getCurrentPageTitle());
    }

    private String getCurrentPageTitle()
    {
        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
    }
}
