package de.kkendzia.myintranet.ei.ui.views.ah.search;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.Route;
import de.kkendzia.myintranet.app.search.queries.SearchAhs;
import de.kkendzia.myintranet.app.search.queries.SearchAhs.ResultItem;
import de.kkendzia.myintranet.ei.core.view.AbstractEIView;
import de.kkendzia.myintranet.ei.core.view.search.SearchRoute;
import de.kkendzia.myintranet.ei.ui.components.menu.provider.annotation.MenuRoute;
import de.kkendzia.myintranet.ei.ui.components.toolbar.ToolbarConfiguration;
import de.kkendzia.myintranet.ei.ui.layouts.SearchLayout;
import de.kkendzia.myintranet.ei.ui.layouts.SearchLayout.NavigationAction.NavigateWithId;
import de.kkendzia.myintranet.ei.ui.layouts.main.EIDrawer.EIMenuKeys;
import de.kkendzia.myintranet.ei.ui.layouts.main.EIMainLayout;
import de.kkendzia.myintranet.ei.ui.layouts.main.EIMainLayoutPresenter.SearchTarget;
import de.kkendzia.myintranet.ei.ui.views.ah.detail.AhDetailView;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;

import static de.kkendzia.myintranet.ei.core.i18n.TranslationKeys.SEARCH;
import static de.kkendzia.myintranet.ei.core.utils.GridColumnFactory.*;
import static de.kkendzia.myintranet.ei.core.view.search.SearchParameters.SEARCH_TEXT;

@Route(value = "ah/search", layout = EIMainLayout.class)
@MenuRoute(label = SEARCH, parent = EIMenuKeys.AH, position = 1)
@SearchRoute(target = SearchTarget.AH)
@PermitAll
public class AhSearchView
        extends AbstractEIView<SearchLayout<ResultItem>>
        implements AfterNavigationObserver
{
    private final AhSearchPresenter presenter;

    @Autowired
    public AhSearchView(AhSearchPresenter presenter)
    {
        this.presenter = presenter;

        // VIEW CONFIG
        setPageTitle(getTranslation("ah.search.pageTitle"));
        setToolbarConfig(new ToolbarConfiguration(getTranslation(SEARCH)));
        registerQueryParameter(SEARCH_TEXT);

        // SEARCH LAYOUT
        SearchLayout<ResultItem> root = getContent();
        root.setNavigationAction(new NavigateWithId<>(AhDetailView.class, ResultItem::id));

        Grid<ResultItem> grid = root.getGrid();
        addCollapsedColumn(grid, getTranslation("label.ahnr"), ResultItem::ahnr);
        addExpandedColumn(grid, getTranslation("label.matchcode"), ResultItem::matchcode);
        addCollapsedColumn(grid, getTranslation("label.enterDate"), ResultItem::enterDate);
        addCollapsedColumn(grid, getTranslation("label.exitDate"), ResultItem::exitDate);
        addSpacerColumn(grid);

        grid.setItems(presenter.getSearchDataProvider());
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event)
    {
        String searchtext = qpValues(SEARCH_TEXT).findFirst().orElse("");

        SearchLayout<ResultItem> layout = getContent();
        layout.setSearchText(searchtext);

        presenter.search(new SearchAhs(searchtext));
    }
}
