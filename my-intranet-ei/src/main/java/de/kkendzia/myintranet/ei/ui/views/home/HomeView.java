package de.kkendzia.myintranet.ei.ui.views.home;

import com.vaadin.flow.component.AttachEvent;
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
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.lumo.LumoUtility.FontSize;
import com.vaadin.flow.theme.lumo.LumoUtility.FontWeight;
import com.vaadin.flow.theme.lumo.LumoUtility.Overflow;
import de.kkendzia.myintranet.ei._framework.view.AbstractEIView;
import de.kkendzia.myintranet.ei.ui.components.menu.provider.annotation.MenuRoute;
import de.kkendzia.myintranet.ei.ui.components.text.MultiLineTitle;
import de.kkendzia.myintranet.ei.ui.layouts.main.EIDrawer;
import de.kkendzia.myintranet.ei.ui.layouts.main.EIMainLayout;
import de.kkendzia.myintranet.ei.ui.views.home.HomeViewPresenter.ActionItem;
import de.kkendzia.myintranet.ei.ui.views.home.HomeViewPresenter.NewsItem;
import de.kkendzia.myintranet.ei.utils.GridColumnFactory;
import jakarta.annotation.security.PermitAll;

import java.util.List;

import static java.util.Objects.requireNonNull;

@Route(value = "", layout = EIMainLayout.class)
@RouteAlias(value = HomeView.NAV, layout = EIMainLayout.class)
@MenuRoute(label = HomeView.MENU_NAME, parent = EIDrawer.EIMenuKeys.START, position = 0)
@PermitAll
public class HomeView extends AbstractEIView<VerticalLayout> implements LocaleChangeObserver
{
    public static final String NAV = "home";
    public static final String MENU_NAME = "menu.home";

    private final MultiLineTitle title = new MultiLineTitle();
    private final Paragraph description = new Paragraph();
    private final Row row1 = new Row();
    private final Row row2 = new Row();
    private final Component gridLabel = createLabel("");
    private final Grid<NewsItem> grid = new Grid<>();
    private final HomeViewPresenter presenter;

    public HomeView(HomeViewPresenter presenter)
    {
        this.presenter = requireNonNull(presenter, "presenter can't be null!");

        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setDetailsVisibleOnClick(true);
        grid.setItemDetailsRenderer(new ComponentRenderer<>(itm -> new Span(itm.message())));
        grid.setAllRowsVisible(true);

        GridColumnFactory.addCollapsedColumn(grid, "Title", NewsItem::title);
        GridColumnFactory.addExpandedColumn(grid, "Message", NewsItem::message);
        GridColumnFactory.addCollapsedColumn(grid, "Seen", NewsItem::seen);

        VerticalLayout root = getContent();
        root.setSizeFull();
        root.setAlignItems(Alignment.STRETCH);
        root.setHeightFull();
        root.setPadding(false);

        root.add(title);
        root.add(description);
        root.add(row1);
        root.add(row2);
        root.add(gridLabel);
        root.addAndExpand(grid);
    }

    @Override
    protected void onAttach(final AttachEvent attachEvent)
    {
        super.onAttach(attachEvent);

        row1.setItems(presenter.fetchActionItems(false, 0, 5).toList());
        row2.setItems(presenter.fetchActionItems(true, 0, 5).toList());

        grid.setItems(presenter.createNewsDataProvider());
    }

    private static Component createLabel(String text)
    {
        final var div = new Div();
        div.addClassName(FontSize.XLARGE);
        div.addClassName(FontWeight.BOLD);
        div.setText(text);
        return div;
    }

    @Override
    public void localeChange(LocaleChangeEvent localeChangeEvent)
    {
        // TODO
        setPageTitle(getTranslation("home.title"));
        title.setPreTitle(getTranslation("app.title.pre"));
        title.setTitle(getTranslation("app.title"));
        description.setText(getTranslation("app.description"));
        row1.setLabel(getTranslation("previous"));
        row2.setLabel(getTranslation("favorites"));
        gridLabel.getElement().setText(getTranslation("news"));

    }

    //region TYPES
    public static class Row extends Composite<VerticalLayout>
    {
        private final Component label = createLabel("");
        private final HorizontalLayout hl = new HorizontalLayout();

        public Row()
        {
            hl.addClassName(Overflow.AUTO);

            final var root = getContent();
            root.setPadding(false);
            root.setAlignItems(Alignment.STRETCH);
            root.add(label);
            root.add(hl);
        }

        public void setLabel(String label)
        {
            this.label.getElement().setText(label);
        }

        public void setItems(List<ActionItem> items)
        {
            items.stream().map(ActionButton::new).forEach(hl::add);
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
