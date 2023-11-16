package de.kkendzia.myintranet.ei.ui.views.home;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.*;
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
import de.kkendzia.myintranet.ei.core.utils.GridColumnFactory;
import de.kkendzia.myintranet.ei.core.view.AbstractEIView;
import de.kkendzia.myintranet.ei.ui.components.menu.provider.annotation.MenuRoute;
import de.kkendzia.myintranet.ei.ui.layouts.main.EIDrawer;
import de.kkendzia.myintranet.ei.ui.layouts.main.EIMainLayout;
import de.kkendzia.myintranet.ei.ui.views.home.HomeViewPresenter.NewsItem;
import jakarta.annotation.security.PermitAll;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

@Route(value = "", layout = EIMainLayout.class)
@RouteAlias(value = HomeView.NAV, layout = EIMainLayout.class)
@MenuRoute(label = HomeView.MENU_NAME, parent = EIDrawer.EIMenuKeys.START, position = 0)
@PermitAll
public class HomeView extends AbstractEIView<VerticalLayout> implements LocaleChangeObserver
{
    public static final String NAV = "home";
    public static final String MENU_NAME = "menu.home";
    private final HorizontalLayout hlRow1 = new HorizontalLayout();
    private final HorizontalLayout hlRow2 = new HorizontalLayout();
    private final Grid<NewsItem> grid = new Grid<>();
    private final HomeViewPresenter presenter;

    public HomeView(HomeViewPresenter presenter)
    {
        this.presenter = requireNonNull(presenter, "presenter can't be null!");

        VerticalLayout vlText = new VerticalLayout();
        vlText.setPadding(false);
        vlText.add(new H2(getTranslation("app.title.pre")));
        vlText.add(new H1(getTranslation("app.title")));
        vlText.add(new Paragraph(getTranslation("app.description")));

//        Image imgHome = new Image(IMG_HOME.getPath(), "HOME");
//        imgHome.addClassNames("home-image");
//        imgHome.addClassNames(HIDE_XL, HIDE_L, HIDE_M);
//        imgHome.setWidth("25em");

        HorizontalLayout hlCenter = new HorizontalLayout();
        hlCenter.add(vlText);
//        hlCenter.add(imgHome);

        GridColumnFactory.addCollapsedColumn(grid, "Title", NewsItem::title);
        GridColumnFactory.addExpandedColumn(grid, "Message", NewsItem::message);
        GridColumnFactory.addCollapsedColumn(grid, "Seen", NewsItem::seen);

        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setDetailsVisibleOnClick(true);
        grid.setItemDetailsRenderer(new ComponentRenderer<>(itm -> new Span(itm.message())));

        VerticalLayout root = getContent();
        root.addClassNames("background-image-home");
        root.setSizeFull();
        root.setAlignItems(Alignment.STRETCH);
        root.setHeightFull();
        root.add(hlCenter);
        root.add(new H3("Zuletzt"));
        root.add(hlRow1);
        root.add(new H3("Favoriten"));
        root.add(hlRow2);
        root.add(new H3("News"));
        root.addAndExpand(grid);
    }

    @Override
    protected void onAttach(final AttachEvent attachEvent)
    {
        super.onAttach(attachEvent);
        presenter
                .fetchActionItems(false, 0, 5)
                .map(HomeView::createButton)
                .filter(Objects::nonNull)
                .forEach(hlRow1::add);

        presenter
                .fetchActionItems(true, 0, 5)
                .map(HomeView::createButton)
                .filter(Objects::nonNull)
                .forEach(hlRow2::add);

        grid.setItems(presenter.createNewsDataProvider());
    }

    private static Component createButton(final HomeViewPresenter.ActionItem action)
    {
        return RouteConfiguration.forSessionScope().getRoute(action.route())
                .map(r ->
                {
                    final var b = new Button(action.title(), VaadinIcon.LIST.create());
                    b.setWidth("10em");
                    b.setHeight("10em");

                    final var link = new RouterLink();
                    link.add(b);
                    link.setRoute(r);
                    return link;
                }).orElse(null);
    }

    @Override
    public void localeChange(LocaleChangeEvent localeChangeEvent)
    {
        // TODO
        setPageTitle(getTranslation("home.title"));
    }
}
