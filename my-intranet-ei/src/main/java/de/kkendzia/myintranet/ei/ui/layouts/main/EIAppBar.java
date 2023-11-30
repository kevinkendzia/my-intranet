package de.kkendzia.myintranet.ei.ui.layouts.main;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.contextmenu.ContextMenu;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;
import de.kkendzia.myintranet.app.useractions.shared.ActionItem;
import de.kkendzia.myintranet.ei.core.components.EIComponent;
import de.kkendzia.myintranet.ei.core.i18n.TranslationKeys;
import de.kkendzia.myintranet.ei.core.session.EISession;
import de.kkendzia.myintranet.ei.core.utils.RoutingUtil;
import de.kkendzia.myintranet.ei.ui.components.menu.provider.annotation.AnnotationItemProvider;
import de.kkendzia.myintranet.ei.ui.components.search.SearchField;
import de.kkendzia.myintranet.ei.ui.layouts.main.EIMainLayoutPresenter.SearchPreviewItem;
import de.kkendzia.myintranet.ei.ui.layouts.main.EIMainLayoutPresenter.SearchTarget;

import static de.kkendzia.myintranet.ei.core.i18n.TranslationKeys.LOGOUT;
import static de.kkendzia.myintranet.ei.core.i18n.TranslationKeys.SEARCH;
import static de.kkendzia.myintranet.ei.ui.layouts.main.EIMainLayoutPresenter.SearchItemType.DEFAULT;
import static de.kkendzia.myintranet.ei.ui.layouts.main.EIMainLayoutPresenter.SearchItemType.FOOTER;

public class EIAppBar
        extends EIComponent<HorizontalLayout>
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
        toggle.setAriaLabel("Menu toggle");

        initSearchField(presenter);

        HorizontalLayout root = getContent();
        root.addClassNames("ei-appbar");
        root.addClassNames(Padding.Right.MEDIUM);
        root.setAlignItems(Alignment.CENTER);
        root.add(toggle);
        root.addAndExpand(searchField);
        root.add(new UserAvatar(session));
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
        SearchTarget target = RoutingUtil.getCurrentSearchTarget();
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
        SearchTarget target = RoutingUtil.getCurrentSearchTarget();
        if (target != null)
        {
            return getTranslation(I18N_SEARCH_IN, getTranslation(target.getKey()));
        }

        String pageTitle = RoutingUtil.getPageTitle();
        return pageTitle != null
               ? getTranslation(I18N_SEARCH_IN, pageTitle)
               : getTranslation(SEARCH);
    }

    public static class UserAvatar extends Composite<Avatar>
    {
        public UserAvatar(EISession session)
        {
            final Avatar parent = getContent();
            createContextMenu(session, parent);
        }

        private void createContextMenu(final EISession session, final Avatar parent)
        {
            ContextMenu ctx = new ContextMenu(parent);
            ctx.setOpenOnClick(true);
            ctx.add(createSessionInfo(session));

            if (!session.getPreviousActions().isEmpty())
            {
                ctx.add(new Hr());
                final var itmPrevious = ctx.addItem(getTranslation("previous"));
                final var subMenuPrevious = itmPrevious.getSubMenu();
                session.getPreviousActions().forEach(a -> subMenuPrevious.addItem(a.title()));
            }

            final var itmFavorites = ctx.addItem(getTranslation("favorites"));
            final var subMenuFavorites = itmFavorites.getSubMenu();
            final var favoriteActions = session.getFavoriteActions();

            subMenuFavorites.addItem(getTranslation("add"), event ->
            {
                final var analyzer = new AnnotationItemProvider.AnnotationAnalyzer();
                final var currentView = UI.getCurrent().getCurrentView();
                final var url = RouteConfiguration.forSessionScope().getUrl(currentView.getClass());
                final var title = analyzer.extractLabel(currentView.getClass());
                session.addFavoriteAction(new ActionItem(title, url));

                subMenuFavorites.addItem(title, e -> UI.getCurrent().navigate(url));
                // 19.11.2023 KK TODO: recreate after add!
            });

            if (!favoriteActions.isEmpty())
            {
                subMenuFavorites.add(new Hr());
                favoriteActions.forEach(a -> subMenuFavorites.addItem(
                        a.title(),
                        e -> UI.getCurrent().navigate(a.route())));
            }

            ctx.addItem(getTranslation(LOGOUT), e -> session.logout());
        }

        private static VerticalLayout createSessionInfo(final EISession session)
        {
            final var vlInfo = new VerticalLayout();
            vlInfo.add(new Span(session.getUserName()));
            vlInfo.add(new Span(String.valueOf(session)));
            return vlInfo;
        }
    }
}
