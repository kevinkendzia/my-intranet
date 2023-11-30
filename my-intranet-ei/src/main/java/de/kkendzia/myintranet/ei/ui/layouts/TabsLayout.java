package de.kkendzia.myintranet.ei.ui.layouts;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.theme.lumo.LumoUtility.Overflow;
import de.kkendzia.myintranet.ei._framework.view.page.EIPage;
import de.kkendzia.myintranet.ei.ui.components.tabs.PagedTabs;

public class TabsLayout<T extends EIPage> extends Composite<VerticalLayout>
{
    private final PagedTabs<T> tabs = new PagedTabs<>();

    public TabsLayout()
    {
        VerticalLayout vlContent = new VerticalLayout();
        vlContent.setPadding(false);
        vlContent.addClassName(Overflow.AUTO);

        tabs.addSelectedPageChangeListener(e ->
        {
            vlContent.removeAll();
            vlContent.add((Component) e.getSelectedTab().getPage());
        });

        VerticalLayout root = getContent();
        root.addClassName("tabs-layout");
        root.setPadding(false);
        root.setAlignItems(FlexComponent.Alignment.STRETCH);
        root.add(tabs);
        root.addAndExpand(vlContent);
    }

    public Registration addSelectedPageChangeListener(ComponentEventListener<PagedTabs.SelectedPageChangeEvent<T>> listener)
    {
        return tabs.addSelectedPageChangeListener(listener);
    }

    public void add(PagedTabs.PagedTab<T> tab)
    {
        tabs.add(tab);
    }

    public PagedTabs<T> getTabs()
    {
        return tabs;
    }

    public void refreshPages()
    {
        tabs.getPages().forEach(EIPage::refresh);
    }
}
