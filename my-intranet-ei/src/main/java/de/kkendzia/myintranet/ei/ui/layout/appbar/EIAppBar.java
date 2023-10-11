package de.kkendzia.myintranet.ei.ui.layout.appbar;

import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;
import de.kkendzia.myintranet.ei.ui._framework.EIComponent;
import de.kkendzia.myintranet.ei.ui._framework.utils.PageTitleUtil;
import de.kkendzia.myintranet.ei.ui.components.search.SearchField;

import java.util.List;

public class EIAppBar extends EIComponent<HorizontalLayout> implements AfterNavigationObserver, LocaleChangeObserver
{
    private SearchField<Test, String> searchField = new SearchField<>();

    public EIAppBar()
    {
        DrawerToggle toggle = new DrawerToggle();
        toggle.setAriaLabel("Menu toggle");

        searchField.setGroupingFunction(Test::group);
        searchField.setItems(
                List.of(
                        new Test("ah", "HOMA"),
                        new Test("ah", "HANS"),
                        new Test("vl", "VL1")));

        HorizontalLayout root = getContent();
        root.addClassNames(Padding.Right.MEDIUM);
        root.add(toggle);
        root.addAndExpand(searchField);
    }

    public static record Test(
            String group,
            String name)
    {
        // just a record
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
