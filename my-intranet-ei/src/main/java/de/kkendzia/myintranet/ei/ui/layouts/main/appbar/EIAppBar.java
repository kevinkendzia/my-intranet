package de.kkendzia.myintranet.ei.ui.layouts.main.appbar;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;
import de.kkendzia.myintranet.ei.core.i18n.TranslationKeys;
import de.kkendzia.myintranet.ei.core.session.EISession;
import de.kkendzia.myintranet.ei.ui.components.search.SearchField;
import de.kkendzia.myintranet.ei.ui.layouts.main.EIMainLayoutPresenter;
import de.kkendzia.myintranet.ei.ui.layouts.main.EIMainLayoutPresenter.SearchPreviewItem;
import de.kkendzia.myintranet.ei.ui.layouts.main.EIMainLayoutPresenter.SearchTarget;
import de.kkendzia.myintranet.ei.utils.RouteUtils;

import static de.kkendzia.myintranet.ei.core.i18n.TranslationKeys.SEARCH;
import static de.kkendzia.myintranet.ei.ui.layouts.main.EIMainLayoutPresenter.SearchItemType.DEFAULT;
import static de.kkendzia.myintranet.ei.ui.layouts.main.EIMainLayoutPresenter.SearchItemType.FOOTER;

public class EIAppBar
        extends Composite<HorizontalLayout>
        implements AfterNavigationObserver, LocaleChangeObserver
{
    //region CONSTANTS
    private static final String I18N_SEARCH_IN = "mainLayout.appbar.searchField.placeholder.searchIn";
    private static final String I18N_SEARCH_ITEM_FOOTER = "mainLayout.appbar.searchField.item.footer";
    //endregion

    private final SearchField<SearchPreviewItem> searchField = new SearchField<>();

    public EIAppBar(EIMainLayoutPresenter presenter, EISession session)
    {
        DrawerToggle toggle = new DrawerToggle();
        // TODO
        toggle.setAriaLabel("Menu toggle");

        initSearchField(presenter);

        HorizontalLayout root = getContent();
        root.addClassNames("ei-appbar");
        root.addClassNames(Padding.Right.MEDIUM);
        root.setAlignItems(Alignment.CENTER);
        root.add(toggle);
        root.addAndExpand(searchField);
        root.add(new UserAvatar(session, presenter));
    }

    private void initSearchField(EIMainLayoutPresenter presenter)
    {
        searchField.setEnabledPredicate(item -> item.type() == DEFAULT || item.type() == FOOTER);
        searchField.setItemLabelGenerator(this::generateSearchItemLabel);
        searchField.addValueChangeListener(e -> presenter.search(e.getValue()));
        searchField.setItemFactory(EIAppBar::createSearchItemFromSearchText);
        searchField.setItems(presenter.createSearchPreviewDataProvider());
    }

    private static SearchPreviewItem createSearchItemFromSearchText(final String searchText)
    {
        SearchTarget target = RouteUtils.getCurrentSearchTarget();
        return new SearchPreviewItem(
                searchText,
                target,
                DEFAULT,
                null,
                searchText);
    }

    private String generateSearchItemLabel(final SearchPreviewItem item)
    {
        final var targetKey = item
                .optionalTarget()
                .map(SearchTarget::getKey)
                .orElse(TranslationKeys.OTHER);
        String target = getTranslation(targetKey);

        return switch (item.type())
        {
            case DEFAULT -> item.name();
            case HEADER -> target;
            case FOOTER -> getTranslation(I18N_SEARCH_ITEM_FOOTER, item.searchText(), target);
        };
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event)
    {
        searchField.setPlaceholder(getSearchPlaceholder());
    }

    @Override
    public void localeChange(LocaleChangeEvent event)
    {
        searchField.setPlaceholder(getSearchPlaceholder());
    }

    private String getSearchPlaceholder()
    {
        SearchTarget target = RouteUtils.getCurrentSearchTarget();
        if (target != null)
        {
            return getTranslation(I18N_SEARCH_IN, getTranslation(target.getKey()));
        }

        String pageTitle = RouteUtils.getPageTitle();
        return pageTitle != null
               ? getTranslation(I18N_SEARCH_IN, pageTitle)
               : getTranslation(SEARCH);
    }

}
