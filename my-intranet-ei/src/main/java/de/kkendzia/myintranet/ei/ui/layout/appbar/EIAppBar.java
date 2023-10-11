package de.kkendzia.myintranet.ei.ui.layout.appbar;

import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import de.kkendzia.myintranet.ei.ui._framework.EIComponent;
import de.kkendzia.myintranet.ei.ui._framework.utils.PageTitleUtil;

public class EIAppBar extends EIComponent<HorizontalLayout> implements AfterNavigationObserver, LocaleChangeObserver
{
    private ComboBox<?> searchField = new ComboBox<>();

    public EIAppBar()
    {
        DrawerToggle toggle = new DrawerToggle();
        toggle.setAriaLabel("Menu toggle");

        HorizontalLayout root = getContent();
        root.add(toggle);
        root.addAndExpand(searchField);
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event)
    {
        // TODO
        String pageTitle = PageTitleUtil.getPageTitle(event.getActiveChain());
        searchField.setPlaceholder(pageTitle != null ? "Search in " + pageTitle : "Search in...");
    }

    @Override
    public void localeChange(LocaleChangeEvent event)
    {
        // TODO
        String pageTitle = PageTitleUtil.getPageTitle();
        searchField.setPlaceholder(pageTitle != null ? "Search in " + pageTitle : "Search in...");
    }
}
