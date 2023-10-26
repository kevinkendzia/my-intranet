package de.kkendzia.myintranet.ei.ui.views.ah.search;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.Route;
import de.kkendzia.myintranet.ei.core.view.search.SearchRoute;
import de.kkendzia.myintranet.ei.core.view.AbstractEIView;
import de.kkendzia.myintranet.ei.ui.layouts.SearchLayout;
import de.kkendzia.myintranet.ei.ui.layouts.SearchLayout.NavigationAction.NavigateWithId;
import de.kkendzia.myintranet.ei.ui.components.toolbar.ToolbarConfiguration;
import de.kkendzia.myintranet.ei.ui.components.menu.provider.annotation.MenuRoute;
import de.kkendzia.myintranet.ei.ui.layouts.main.EIDrawer.EIMenuKeys;
import de.kkendzia.myintranet.ei.ui.layouts.main.EIMainLayout;
import de.kkendzia.myintranet.ei.ui.layouts.main.EIMainLayoutPresenter.SearchTarget;
import de.kkendzia.myintranet.ei.ui.views.ah.detail.AhDetailView;
import de.kkendzia.myintranet.ei.ui.views.ah.search.AhSearchPresenter.SearchItem;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;

import static de.kkendzia.myintranet.ei.core.i18n.TranslationKeys.SEARCH;
import static de.kkendzia.myintranet.ei.core.view.search.SearchParameters.SEARCH_TEXT;
import static de.kkendzia.myintranet.ei.core.utils.GridColumnFactory.*;

@Route(value = "ah/search", layout = EIMainLayout.class)
@MenuRoute(label = SEARCH, parent = EIMenuKeys.AH, position = 1)
@SearchRoute(target = SearchTarget.AH)
@PermitAll
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
        setToolbarConfig(new ToolbarConfiguration(getTranslation(SEARCH)));
        registerQueryParameter(SEARCH_TEXT);

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
        String searchtext = qpValues(SEARCH_TEXT).findFirst().orElse("");

        SearchLayout<SearchItem> layout = getContent();
        layout.setSearchText(searchtext);
        layout.getGrid().setItems(presenter.createSearchDataProvider(searchtext));
    }
}
