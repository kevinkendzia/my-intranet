package de.kkendzia.myintranet.ei.ui.views.home;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.FlexLayout.FlexWrap;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.QuerySortOrder;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import de.kkendzia.myintranet.app.news.queries.FindCurrentNews.NewsItem;
import de.kkendzia.myintranet.app.notification.queries.FindUnseenNotifications.NotificationItem;
import de.kkendzia.myintranet.app.useractions._shared.ActionItem;
import de.kkendzia.myintranet.domain.user.EIUserAction;
import de.kkendzia.myintranet.ei._framework.view.AbstractEIView;
import de.kkendzia.myintranet.ei.ui.components.text.MultiLineTitle;
import de.kkendzia.myintranet.ei.ui.layouts.main.EIMainLayout;
import de.kkendzia.myintranet.ei.ui.layouts.main.drawer.EIDrawer;
import de.kkendzia.myintranet.ei.ui.layouts.main.drawer.menu.provider.annotation.MenuRoute;
import de.kkendzia.myintranet.ei.ui.views.home.components.HomeActionButton;
import de.kkendzia.myintranet.ei.ui.views.home.components.HorizontalList;
import de.kkendzia.myintranet.ei.utils.GridColumnFactory;
import jakarta.annotation.security.PermitAll;

import java.util.List;

import static de.kkendzia.myintranet.ei.core.i18n.TranslationKeys.*;
import static de.kkendzia.myintranet.ei.core.i18n.TranslationKeys.AppKeys.APP_DESCRIPTION;
import static de.kkendzia.myintranet.ei.core.i18n.TranslationKeys.AppKeys.APP_WELCOME;
import static de.kkendzia.myintranet.ei.ui.components.ComponentFactory.xLargeLabel;
import static java.util.Comparator.comparing;
import static java.util.Objects.requireNonNull;

@Route(value = "", layout = EIMainLayout.class)
@RouteAlias(value = HomeView.NAV, layout = EIMainLayout.class)
@MenuRoute(label = HomeView.MENU_NAME, parent = EIDrawer.EIMenuKeys.START, position = 0)
@PermitAll
public class HomeView extends AbstractEIView<VerticalLayout>
{
    public static final String NAV = "home";
    public static final String MENU_NAME = "menu.home";
    private static final String HOME_TITLE = "home.title";

    public HomeView(HomeViewPresenter presenter)
    {
        requireNonNull(presenter, "presenter can't be null!");

        registerLocaleChangeConsumer(e -> setPageTitle(getTranslation(HOME_TITLE)));

        /*
         * HEADER
         */

        final MultiLineTitle title = new MultiLineTitle();
        registerLocaleChangeConsumer(e ->
        {
            title.setPreTitle(getTranslation(APP_WELCOME));
            title.setTitle(getTranslation(AppKeys.APP_TITLE));
        });

        final Paragraph description = new Paragraph();
        registerLocaleChangeConsumer(e -> description.setText(getTranslation(APP_DESCRIPTION)));

        /*
         * ACTIONS
         */

        final HorizontalList<ActionItem> row1 = new HorizontalList<>();
        registerLocaleChangeConsumer(e -> row1.setLabel(getTranslation(RECENT)));
        row1.setRenderer(new ComponentRenderer<>(HomeActionButton::new));
        row1.setComparator(comparing(ActionItem::getTimestamp).reversed());
        row1.setSortOrders(List.of(new QuerySortOrder(EIUserAction.PROPERTY_TIMESTAMP, SortDirection.DESCENDING)));
        row1.setItems(presenter.createRecentActionDataProvider());

        final HorizontalList<ActionItem> row2 = new HorizontalList<>();
        registerLocaleChangeConsumer(e -> row2.setLabel(getTranslation(FAVORITES)));
        row2.setRenderer(new ComponentRenderer<>(HomeActionButton::new));
        row2.setComparator(comparing(ActionItem::getTimestamp).reversed());
        row2.setSortOrders(List.of(new QuerySortOrder(EIUserAction.PROPERTY_TIMESTAMP, SortDirection.DESCENDING)));
        row2.setItems(presenter.createFavoriteActionDataProvider());

        /*
         * NOTIFICATIONS
         */

        final Component notificationsGridLabel = xLargeLabel("");
        registerLocaleChangeConsumer(e -> notificationsGridLabel.getElement().setText(getTranslation(NOTIFICATIONS)));

        Grid<NotificationItem> notificationsGrid = new Grid<>();
        notificationsGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        notificationsGrid.setDetailsVisibleOnClick(true);
        notificationsGrid.setItemDetailsRenderer(new ComponentRenderer<>(itm -> new Span(itm.message())));
        notificationsGrid.setAllRowsVisible(true);

        final var notificationColums = new GridColumnFactory<>(notificationsGrid);
        notificationColums.addCollapsedColumn("Title", NotificationItem::title);
        notificationColums.addExpandedColumn("Message", NotificationItem::message);

        notificationsGrid.setItems(presenter.createNotificationsDataProvider());

        final var vlNotifications = new VerticalLayout();
        vlNotifications.setPadding(false);
        vlNotifications.add(notificationsGridLabel);
        vlNotifications.add(notificationsGrid);

        /*
         * NEWS
         */

        final Component newsGridLabel = xLargeLabel("");
        registerLocaleChangeConsumer(e -> newsGridLabel.getElement().setText(getTranslation(NEWS)));

        Grid<NewsItem> newsGrid = new Grid<>();
        newsGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        newsGrid.setDetailsVisibleOnClick(true);
        newsGrid.setItemDetailsRenderer(new ComponentRenderer<>(itm -> new Span(itm.message())));
        newsGrid.setAllRowsVisible(true);

        final var newsColums = new GridColumnFactory<>(newsGrid);
        newsColums.addCollapsedColumn("Title", NewsItem::title);
        newsColums.addExpandedColumn("Message", NewsItem::message);

        newsGrid.setItems(presenter.createNewsDataProvider());

        final var vlNews = new VerticalLayout();
        vlNews.setPadding(false);
        vlNews.add(newsGridLabel);
        vlNews.add(newsGrid);

        /*
         * GRID - FLEX
         */

        final var flexLayout = new FlexLayout();
        flexLayout.addClassName(Gap.MEDIUM);
        flexLayout.setFlexWrap(FlexWrap.WRAP);
        flexLayout.add(vlNotifications);
        flexLayout.setFlexBasis("10em", vlNotifications);
        flexLayout.setFlexShrink(1, vlNotifications);
        flexLayout.setFlexGrow(1, vlNotifications);
        flexLayout.add(vlNews);
        flexLayout.setFlexBasis("10em", vlNews);
        flexLayout.setFlexShrink(1, vlNews);
        flexLayout.setFlexGrow(1, vlNews);

        /*
         * ROOT
         */

        VerticalLayout root = getContent();
        root.setSizeFull();
        root.setAlignItems(Alignment.STRETCH);
        root.setHeightFull();
        root.setPadding(false);

        root.add(title);
        root.add(description);
        root.add(row1);
        root.add(row2);
        root.addAndExpand(flexLayout);
    }
}
