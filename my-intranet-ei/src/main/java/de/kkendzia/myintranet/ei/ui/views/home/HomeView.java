package de.kkendzia.myintranet.ei.ui.views.home;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.lumo.LumoUtility.FontSize;
import com.vaadin.flow.theme.lumo.LumoUtility.FontWeight;
import com.vaadin.flow.theme.lumo.LumoUtility.Overflow;
import de.kkendzia.myintranet.app.news.queries.FindCurrentNews.NewsItem;
import de.kkendzia.myintranet.app.notification.queries.FindUnseenNotifications.NotificationItem;
import de.kkendzia.myintranet.app.useractions.shared.ActionItem;
import de.kkendzia.myintranet.ei._framework.view.AbstractEIView;
import de.kkendzia.myintranet.ei.ui.components.text.MultiLineTitle;
import de.kkendzia.myintranet.ei.ui.layouts.main.EIMainLayout;
import de.kkendzia.myintranet.ei.ui.layouts.main.drawer.EIDrawer;
import de.kkendzia.myintranet.ei.ui.layouts.main.drawer.menu.provider.annotation.MenuRoute;
import de.kkendzia.myintranet.ei.utils.GridColumnFactory;
import jakarta.annotation.security.PermitAll;

import static java.util.Collections.emptyList;
import static java.util.Objects.requireNonNull;

@Route(value = "", layout = EIMainLayout.class)
@RouteAlias(value = HomeView.NAV, layout = EIMainLayout.class)
@MenuRoute(label = HomeView.MENU_NAME, parent = EIDrawer.EIMenuKeys.START, position = 0)
@PermitAll
public class HomeView extends AbstractEIView<VerticalLayout>
{
    public static final String NAV = "home";
    public static final String MENU_NAME = "menu.home";

    public HomeView(HomeViewPresenter presenter)
    {
        requireNonNull(presenter, "presenter can't be null!");

        registerLocaleChangeConsumer(e -> setPageTitle(getTranslation("home.title")));

        /*
         * HEADER
         */

        final MultiLineTitle title = new MultiLineTitle();
        registerLocaleChangeConsumer(e ->
        {
            title.setPreTitle(getTranslation("app.title.pre"));
            title.setTitle(getTranslation("app.title"));
        });

        final Paragraph description = new Paragraph();
        registerLocaleChangeConsumer(e -> description.setText(getTranslation("app.description")));

        /*
         * ACTIONS
         */

        final HorizontalList<ActionItem> row1 = new HorizontalList<>();
        registerLocaleChangeConsumer(e -> row1.setLabel(getTranslation("previous")));
        row1.setRenderer(new ComponentRenderer<>(ActionButton::new));
        row1.setItems(presenter.createRecentActionDataProvider());

        final HorizontalList<ActionItem> row2 = new HorizontalList<>();
        registerLocaleChangeConsumer(e -> row2.setLabel(getTranslation("favorites")));
        row2.setRenderer(new ComponentRenderer<>(ActionButton::new));
        row2.setItems(presenter.createFavoriteActionDataProvider());

        /*
         * NOTIFICATIONS
         */

        final Component notificationsGridLabel = createLabel("");
        registerLocaleChangeConsumer(e -> notificationsGridLabel.getElement().setText(getTranslation("notifications")));

        Grid<NotificationItem> notificationsGrid = new Grid<>();
        notificationsGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        notificationsGrid.setDetailsVisibleOnClick(true);
        notificationsGrid.setItemDetailsRenderer(new ComponentRenderer<>(itm -> new Span(itm.message())));
        notificationsGrid.setAllRowsVisible(true);

        final var notificationColums = new GridColumnFactory<>(notificationsGrid);
        notificationColums.addCollapsedColumn("Title", NotificationItem::title);
        notificationColums.addExpandedColumn("Message", NotificationItem::message);

        notificationsGrid.setItems(presenter.createNotificationsDataProvider());

        /*
         * NEWS
         */

        final Component newsGridLabel = createLabel("");
        registerLocaleChangeConsumer(e -> newsGridLabel.getElement().setText(getTranslation("news")));

        Grid<NewsItem> newsGrid = new Grid<>();
        newsGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        newsGrid.setDetailsVisibleOnClick(true);
        newsGrid.setItemDetailsRenderer(new ComponentRenderer<>(itm -> new Span(itm.message())));
        newsGrid.setAllRowsVisible(true);

        final var newsColums = new GridColumnFactory<>(newsGrid);
        newsColums.addCollapsedColumn("Title", NewsItem::title);
        newsColums.addExpandedColumn("Message", NewsItem::message);

        newsGrid.setItems(presenter.createNewsDataProvider());

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
        root.add(notificationsGridLabel);
        root.addAndExpand(notificationsGrid);
        root.add(newsGridLabel);
        root.addAndExpand(newsGrid);
    }

    private static Component createLabel(String text)
    {
        final var div = new Div();
        div.addClassName(FontSize.XLARGE);
        div.addClassName(FontWeight.BOLD);
        div.setText(text);
        return div;
    }

    //region TYPES
    public static class HorizontalList<T> extends Composite<VerticalLayout>
    {
        private final Component label = createLabel("");
        private final HorizontalLayout hl = new HorizontalLayout();

        private ComponentRenderer<?, T> renderer;
        private int pageSize = 5;

        public HorizontalList()
        {
            hl.addClassName(Overflow.AUTO);

            final var root = getContent();
            root.setPadding(false);
            root.setAlignItems(Alignment.STRETCH);
            root.setHeight("10em");
            root.add(label);
            root.add(hl);
        }

        public void setLabel(String label)
        {
            this.label.getElement().setText(label);
        }

        public void setRenderer(final ComponentRenderer<?, T> renderer)
        {
            this.renderer = renderer;
        }

        public void setPageSize(final int pageSize)
        {
            this.pageSize = pageSize;
        }

        public void setItems(DataProvider<T, Void> items)
        {
            hl.removeAll();

            final var size = items.size(new Query<>());
            if (size > 0)
            {
                items.fetch(new Query<>(0, pageSize, emptyList(), null, null))
                        .map(i ->
                        {
                            if (renderer != null)
                            {
                                return renderer.createComponent(i);
                            }
                            return new Span(String.valueOf(i));
                        })
                        .forEach(hl::add);
            }
            else
            {
                // TODO
                hl.add(new Span("NO DATA!"));
            }
        }
    }

    public static class ActionButton extends Composite<RouterLink>
    {
        public ActionButton(ActionItem action)
        {
            final var b = new Button(action.title(), VaadinIcon.LIST.create());
            b.setWidth("10em");
            b.setHeight("10em");

            final var link = getContent();
            link.add(b);

            RouteConfiguration
                    .forSessionScope()
                    .getRoute(action.route())
                    .ifPresent(link::setRoute);
        }

        public void setRoute(Class<Component> route)
        {
            getContent().setRoute(route);
        }
    }
    //endregion
}
