package de.kkendzia.myintranet.ei.ui.views.mandant.search;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.Route;
import de.kkendzia.myintranet.app.search.queries.SearchMandanten;
import de.kkendzia.myintranet.app.search.queries.SearchMandanten.ResultItem;
import de.kkendzia.myintranet.ei.core.view.AbstractEIView;
import de.kkendzia.myintranet.ei.core.view.search.SearchParameters;
import de.kkendzia.myintranet.ei.ui.components.menu.provider.annotation.MenuRoute;
import de.kkendzia.myintranet.ei.ui.components.navigation.NavigateWithItem;
import de.kkendzia.myintranet.ei.ui.components.toolbar.ToolbarConfiguration;
import de.kkendzia.myintranet.ei.ui.layouts.SearchLayout;
import de.kkendzia.myintranet.ei.ui.layouts.main.EIMainLayout;
import de.kkendzia.myintranet.ei.ui.views.mandant.detail.MandantDetailView;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;

import static de.kkendzia.myintranet.ei.core.i18n.TranslationKeys.NAME;
import static de.kkendzia.myintranet.ei.core.i18n.TranslationKeys.SEARCH;
import static de.kkendzia.myintranet.ei.core.utils.GridColumnFactory.addCollapsedColumn;
import static de.kkendzia.myintranet.ei.core.utils.GridColumnFactory.addSpacerColumn;
import static de.kkendzia.myintranet.ei.ui.layouts.main.EIDrawer.EIMenuKeys.MANDANTEN;
import static de.kkendzia.myintranet.ei.ui.views.mandant.routes.MandantRoutes.NAV_ROOT;
import static java.util.Objects.requireNonNull;

@Route(value = MandantSearchView.NAV, layout = EIMainLayout.class)
@MenuRoute(label = SEARCH, parent = MANDANTEN, position = 1)
@PermitAll
public final class MandantSearchView extends AbstractEIView<SearchLayout<ResultItem>>
{
    public static final String NAV = NAV_ROOT + "/search";

    private final MandantSearchPresenter presenter;

    @Autowired
    public MandantSearchView(MandantSearchPresenter presenter)
    {
        this.presenter = requireNonNull(presenter, "presenter can't be null!");

        setToolbarConfig(new ToolbarConfiguration(getTranslation(SEARCH)));

        SearchLayout<ResultItem> root = getContent();
        root.setNavigationAction(new NavigateWithItem<>(MandantDetailView.class, ResultItem::idString));

        Grid<ResultItem> grid = root.getGrid();
        addCollapsedColumn(grid, getTranslation(NAME), ResultItem::name);
        addSpacerColumn(grid);

        root.setItems(presenter.getSearchDataProvider());
    }

    @Override
    public void beforeEnterView(BeforeEnterEvent event)
    {
        String searchtext = qpValues(SearchParameters.SEARCH_TEXT).findFirst().orElse("");

        SearchLayout<ResultItem> layout = getContent();
        layout.setSearchText(searchtext);
        presenter.search(new SearchMandanten(searchtext));
    }
}
