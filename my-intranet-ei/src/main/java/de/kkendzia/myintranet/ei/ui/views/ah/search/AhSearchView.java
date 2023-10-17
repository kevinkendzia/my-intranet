package de.kkendzia.myintranet.ei.ui.views.ah.search;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.Route;
import de.kkendzia.myintranet.ei.core.search.SearchParameters;
import de.kkendzia.myintranet.ei.core.search.SearchRoute;
import de.kkendzia.myintranet.ei.core.view.AbstractEIView;
import de.kkendzia.myintranet.ei.core.view.SearchLayout;
import de.kkendzia.myintranet.ei.core.view.SearchLayout.NavigationAction.NavigateWithId;
import de.kkendzia.myintranet.ei.ui.components.menu.provider.AnnotationItemProvider.MenuRoute;
import de.kkendzia.myintranet.ei.ui.layout.EIMainLayout;
import de.kkendzia.myintranet.ei.ui.views.ah.detail.AhDetailView;
import de.kkendzia.myintranet.ei.ui.views.ah.search.AhSearchPresenter.SearchItem;
import org.springframework.beans.factory.annotation.Autowired;

import static de.kkendzia.myintranet.ei.core.utils.GridColumnFactory.*;
import static de.kkendzia.myintranet.ei.ui.layout.EIMainLayoutPresenter.SearchTarget.AH;

@Route(value = "ah/search", layout = EIMainLayout.class)
@MenuRoute(label = "menu.search", parent = "ah", position = 1)
@SearchRoute(target = AH)
public class AhSearchView
        extends AbstractEIView<SearchLayout<SearchItem>>
        implements AfterNavigationObserver
{
    private final AhSearchPresenter presenter;

    @Autowired
    public AhSearchView(AhSearchPresenter presenter)
    {
        this.presenter = presenter;

        // VIEW CONFIG
        setPageTitle(getTranslation("ah.search.pageTitle"));
        registerQueryParameter(SearchParameters.SEARCH_TEXT);

        // SEARCH LAYOUT
        SearchLayout<SearchItem> root = getContent();
        root.setNavigationAction(new NavigateWithId<>(AhDetailView.class, SearchItem::id));

        Grid<SearchItem> grid = root.getGrid();
        addCollapsedColumn(grid, getTranslation("label.ahnr"), SearchItem::ahnr);
        addExpandedColumn(grid, getTranslation("label.matchcode"), SearchItem::matchcode);
        addCollapsedColumn(grid, getTranslation("label.enterDate"), SearchItem::enterDate);
        addCollapsedColumn(grid, getTranslation("label.exitDate"), SearchItem::exitDate);
        addSpacerColumn(grid);
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event)
    {
        String searchtext = getFirstQueryParameterValue(SearchParameters.SEARCH_TEXT);

        SearchLayout<SearchItem> layout = getContent();
        layout.setSearchText(searchtext);
        layout.getGrid().setItems(presenter.createSearchDataProvider(searchtext));
    }
}
