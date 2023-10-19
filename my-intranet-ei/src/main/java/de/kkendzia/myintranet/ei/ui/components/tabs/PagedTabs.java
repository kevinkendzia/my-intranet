package de.kkendzia.myintranet.ei.ui.components.tabs;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.shared.Registration;

import java.util.List;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

public class PagedTabs<C extends HasElement> extends Composite<Tabs>
{
    @SuppressWarnings("unchecked")
    public PagedTabs()
    {
        getContent().addSelectedChangeListener(e ->
        {
            PagedTab<C> prevTab = (PagedTab<C>) e.getPreviousTab();
            PagedTab<C> selectedTab = (PagedTab<C>) e.getSelectedTab();
            fireEvent(new SelectedPageChangeEvent<>(this, e.isFromClient(), prevTab, selectedTab));
        });
    }

    public void add(PagedTab<C> tab)
    {
        getContent().add(tab);
    }

    public Registration addSelectedPageChangeListener(ComponentEventListener<SelectedPageChangeEvent<C>> listener)
    {
        //noinspection unchecked,rawtypes
        return addListener(SelectedPageChangeEvent.class, (ComponentEventListener) listener);
    }

    public C getSelectedPage()
    {
        PagedTab<C> selectedTab = getSelectedTab();
        return selectedTab.getPage();
    }

    public PagedTab<C> getSelectedTab()
    {
        //noinspection unchecked
        return (PagedTab<C>) getContent().getSelectedTab();
    }

    public Stream<PagedTab<C>> getTabs()
    {
        //noinspection unchecked
        return getContent().getChildren().map(x -> (PagedTab<C>) x);
    }
    public List<C> getPages()
    {
        return getTabs().map(PagedTab::getPage).toList();
    }

    public static class PagedTab<C extends HasElement> extends Tab
    {
        private final C page;

        public PagedTab(
                String label,
                C page)
        {
            super(label);
            this.page = requireNonNull(page, "page can't be null!");
        }

        public C getPage()
        {
            return page;
        }
    }

    public static class SelectedPageChangeEvent<C extends HasElement> extends ComponentEvent<PagedTabs<C>>
    {
        private final PagedTab<C> previousTab;
        private final PagedTab<C> selectedTab;

        public SelectedPageChangeEvent(
                PagedTabs<C> source,
                boolean fromClient,
                PagedTab<C> previousTab,
                PagedTab<C> selectedTab)
        {
            super(source, fromClient);
            this.previousTab = previousTab;
            this.selectedTab = selectedTab;
        }

        public PagedTab<C> getPreviousTab()
        {
            return previousTab;
        }

        public PagedTab<C> getSelectedTab()
        {
            return selectedTab;
        }
    }
}
