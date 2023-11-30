package de.kkendzia.myintranet.ei.ui.views.ah.search;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.Route;
import de.kkendzia.myintranet.app.search.queries.SearchAhs;
import de.kkendzia.myintranet.app.search.queries.SearchAhs.ResultItem;
import de.kkendzia.myintranet.ei._framework.view.AbstractEIView;
import de.kkendzia.myintranet.ei._framework.view.search.SearchRoute;
import de.kkendzia.myintranet.ei.core.i18n.TranslationKeys;
import de.kkendzia.myintranet.ei.ui.layouts.main.drawer.menu.provider.annotation.MenuRoute;
import de.kkendzia.myintranet.ei.ui.components.navigation.NavigateWithItem;
import de.kkendzia.myintranet.ei.ui.components.toolbar.ToolbarConfiguration;
import de.kkendzia.myintranet.ei.ui.layouts.SearchLayout;
import de.kkendzia.myintranet.ei.ui.layouts.main.drawer.EIDrawer.EIMenuKeys;
import de.kkendzia.myintranet.ei.ui.layouts.main.EIMainLayout;
import de.kkendzia.myintranet.ei.ui.layouts.main.EIMainLayoutPresenter.SearchTarget;
import de.kkendzia.myintranet.ei.ui.views.ah.detail.AhDetailView;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;

import static de.kkendzia.myintranet.ei._framework.view.search.SearchParameters.SEARCH_TEXT;
import static de.kkendzia.myintranet.ei.core.i18n.TranslationKeys.*;
import static de.kkendzia.myintranet.ei.utils.GridColumnFactory.*;

@Route(value = "ah/search", layout = EIMainLayout.class)
@MenuRoute(label = TranslationKeys.SEARCH, parent = EIMenuKeys.AH, position = 1)
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
        setPageTitle(getTranslation(AhKeys.SEARCH));
        setToolbarConfig(new ToolbarConfiguration(getTranslation(AhKeys.SEARCH)));
        registerQueryParameter(SEARCH_TEXT);

        // SEARCH LAYOUT
        SearchLayout<ResultItem> root = getContent();
        root.setNavigationAction(new NavigateWithItem<>(AhDetailView.class, ResultItem::idString));

        Grid<ResultItem> grid = root.getGrid();
        addCollapsedColumn(grid, getTranslation(AhKeys.AHNR), ResultItem::ahnr);
        addExpandedColumn(grid, getTranslation(MATCHCODE), ResultItem::matchcode);
        addCollapsedColumn(grid, getTranslation(ENTER_DATE), ResultItem::enterDate);
        addCollapsedColumn(grid, getTranslation(EXIT_DATE), ResultItem::exitDate);
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
