package de.kkendzia.myintranet.ei.ui.layout;

import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;
import de.kkendzia.myintranet.ei.core.EIComponent;
import de.kkendzia.myintranet.ei.core.utils.RoutingUtil;
import de.kkendzia.myintranet.ei.ui.components.search.SearchField;
import de.kkendzia.myintranet.ei.ui.layout.EIMainLayoutPresenter.SearchPreviewItem;
import de.kkendzia.myintranet.ei.ui.layout.EIMainLayoutPresenter.SearchTarget;

import static de.kkendzia.myintranet.ei.ui.layout.EIMainLayoutPresenter.SearchItemType.DEFAULT;
import static de.kkendzia.myintranet.ei.ui.layout.EIMainLayoutPresenter.SearchItemType.FOOTER;
import static de.kkendzia.myintranet.ei.ui.layout.EIMainLayoutPresenter.SearchTarget.OTHER;
import static java.util.Objects.requireNonNullElse;

public class EIAppBar
        extends EIComponent<HorizontalLayout>
        implements AfterNavigationObserver, LocaleChangeObserver
{
    //region CONSTANTS
    private static final String I18N_SEARCH_IN = "mainLayout.appbar.searchField.placeholder.searchIn";
    private static final String I18N_SEARCH = "mainLayout.appbar.searchField.placeholder.search";
    private static final String I18N_SEARCH_ITEM_FOOTER = "mainLayout.appbar.searchField.item.footer";
    //endregion

    private final SearchField<SearchPreviewItem> searchField = new SearchField<>();

    public EIAppBar(EIMainLayoutPresenter presenter)
    {
        DrawerToggle toggle = new DrawerToggle();
        toggle.setAriaLabel("Menu toggle");

        searchField.setEnabledPredicate(item -> item.type() == DEFAULT || item.type() == FOOTER);
        searchField.setItemLabelGenerator(item ->
        {
            String target = getTranslation(item.target().getKey());

            return switch (item.type())
            {
                case DEFAULT -> item.name();
                case HEADER -> target;
                case FOOTER -> getTranslation(I18N_SEARCH_ITEM_FOOTER, item.searchText(), target);
            };
        });
        searchField.addValueChangeListener(e -> presenter.search(e.getValue()));
        searchField.setItemCreator(searchText ->
        {
            SearchTarget target = RoutingUtil.getCurrentSearchTarget();
            return new SearchPreviewItem(
                    searchText,
                    requireNonNullElse(target, OTHER),
                    DEFAULT,
                    -1,
                    searchText);
        });
        searchField.setItems(presenter.createSearchPreviewDataProvider());

        HorizontalLayout root = getContent();
        root.addClassNames(Padding.Right.MEDIUM);
        root.add(toggle);
        root.addAndExpand(searchField);
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event)
    {
        searchField.setPlaceholder(getPlaceholder());
    }

    @Override
    public void localeChange(LocaleChangeEvent event)
    {
        searchField.setPlaceholder(getPlaceholder());
    }

    private String getPlaceholder()
    {
        SearchTarget target = RoutingUtil.getCurrentSearchTarget();
        if (target != null)
        {
            return getTranslation(I18N_SEARCH_IN, getTranslation(target.getKey()));
        }

        String pageTitle = RoutingUtil.getPageTitle();
        return pageTitle != null
               ? getTranslation(I18N_SEARCH_IN, pageTitle)
               : getTranslation(I18N_SEARCH);
    }
}
