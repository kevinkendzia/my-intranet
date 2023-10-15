package de.kkendzia.myintranet.ei.ui.views.ah.search;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.Route;
import de.kkendzia.myintranet.ei.core.search.SearchParameters;
import de.kkendzia.myintranet.ei.core.search.SearchRoute;
import de.kkendzia.myintranet.ei.core.view.AbstractEIView;
import de.kkendzia.myintranet.ei.ui.components.menu.provider.AnnotationItemProvider.MenuRoute;
import de.kkendzia.myintranet.ei.ui.layout.EIMainLayout;
import de.kkendzia.myintranet.ei.ui.views.ah.detail.AhDetailView;
import de.kkendzia.myintranet.ei.ui.views.ah.search.AhSearchPresenter.SearchItem;
import org.springframework.beans.factory.annotation.Autowired;

import static de.kkendzia.myintranet.ei.core.utils.GridColumnFactory.*;
import static de.kkendzia.myintranet.ei.ui.layout.EIMainLayoutPresenter.SearchTarget.AH;

@Route(value = "ah/search", layout = EIMainLayout.class)
@MenuRoute(label = "menu.search", parent = "ah/search")
@SearchRoute(target = AH)
public class AhSearchView
        extends AbstractEIView<VerticalLayout>
        implements AfterNavigationObserver
{
    private final Span spSearchText = new Span();
    private final AhSearchPresenter presenter;
    private final Grid<SearchItem> grid = new Grid<>();

    @Autowired
    public AhSearchView(AhSearchPresenter presenter)
    {
        this.presenter = presenter;

        setPageTitle(getTranslation("ah.search.pageTitle"));
        registerQueryParameter(SearchParameters.SEARCH_TEXT);

        grid.addItemClickListener(e -> UI.getCurrent().navigate(AhDetailView.class, e.getItem().id()));

        addCollapsedColumn(grid, getTranslation("label.ahnr"), SearchItem::ahnr);
        addExpandedColumn(grid, getTranslation("label.matchcode"), SearchItem::matchcode);
        addCollapsedColumn(grid, getTranslation("label.enterDate"), SearchItem::enterDate);
        addCollapsedColumn(grid, getTranslation("label.exitDate"), SearchItem::exitDate);
        addSpacerColumn(grid);

        VerticalLayout root = getContent();
        root.setHeightFull();
        root.add(new H3("AH SEARCH"));
        root.add(new HorizontalLayout(new Span("Searchtext:"), spSearchText));
        root.addAndExpand(grid);
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event)
    {
        String searchtext = getFirstQueryParameterValue(SearchParameters.SEARCH_TEXT);
        spSearchText.setText(searchtext);
        grid.setItems(presenter.createSearchDataProvider(searchtext));
    }
}
