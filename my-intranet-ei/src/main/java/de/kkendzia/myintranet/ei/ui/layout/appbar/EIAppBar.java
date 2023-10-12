package de.kkendzia.myintranet.ei.ui.layout.appbar;

import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.ItemLabelGenerator;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.function.SerializableFunction;
import com.vaadin.flow.function.SerializablePredicate;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;
import de.kkendzia.myintranet.ei.ui._framework.EIComponent;
import de.kkendzia.myintranet.ei.ui._framework.utils.PageTitleUtil;
import de.kkendzia.myintranet.ei.ui.components.search.SearchField;
import de.kkendzia.myintranet.ei.ui.layout.EIMainLayoutPresenter.SearchPreviewItem;

public class EIAppBar
        extends EIComponent<HorizontalLayout>
        implements AfterNavigationObserver, LocaleChangeObserver
{
    private SearchField<SearchPreviewItem> searchField = new SearchField<>();

    public EIAppBar(
            DataProvider<SearchPreviewItem, String> searchDataProvider,
            SearchChangeListener<SearchPreviewItem> valueChangeListener,
            SerializablePredicate<SearchPreviewItem> searchItemEnabledPredicate,
            ItemLabelGenerator<SearchPreviewItem> searchItemTitleGenerator,
            SerializableFunction<String, SearchPreviewItem> itemCreator)
    {
        DrawerToggle toggle = new DrawerToggle();
        toggle.setAriaLabel("Menu toggle");

        searchField.setEnabledPredicate(searchItemEnabledPredicate);
        searchField.setTitleGenerator(searchItemTitleGenerator);
        searchField.setItems(searchDataProvider);
        searchField.addValueChangeListener(valueChangeListener);
        searchField.setItemCreator(itemCreator);

        HorizontalLayout root = getContent();
        root.addClassNames(Padding.Right.MEDIUM);
        root.add(toggle);
        root.addAndExpand(searchField);
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event)
    {
        // TODO
        String pageTitle = PageTitleUtil.getPageTitle(event.getActiveChain());
        searchField.setPlaceholder(pageTitle != null
                                   ? "Search in " + pageTitle
                                   : "Search in...");
    }

    @Override
    public void localeChange(LocaleChangeEvent event)
    {
        // TODO
        String pageTitle = PageTitleUtil.getPageTitle();
        searchField.setPlaceholder(pageTitle != null
                                   ? "Search in " + pageTitle
                                   : "Search in...");
    }

    //region TYPES
    public interface SearchChangeListener<T> extends HasValue.ValueChangeListener<HasValue.ValueChangeEvent<T>>
    {
        // just Type Alias
    }
    //endregion
}
