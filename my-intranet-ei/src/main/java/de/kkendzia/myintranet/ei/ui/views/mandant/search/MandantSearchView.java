package de.kkendzia.myintranet.ei.ui.views.mandant.search;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.Route;
import de.kkendzia.myintranet.app.service.mandant.MandantSearchService.MandantSearchFilters;
import de.kkendzia.myintranet.app.service.mandant.MandantSearchService.MandantSearchItem;
import de.kkendzia.myintranet.ei.core.search.SearchParameters;
import de.kkendzia.myintranet.ei.core.view.AbstractEIView;
import de.kkendzia.myintranet.ei.core.view.layouts.SearchLayout;
import de.kkendzia.myintranet.ei.core.view.layouts.SearchLayout.NavigationAction.NavigateWithId;
import de.kkendzia.myintranet.ei.ui.components.menu.provider.annotation.MenuRoute;
import de.kkendzia.myintranet.ei.ui.layout.EIMainLayout;
import de.kkendzia.myintranet.ei.ui.views.mandant.detail.MandantDetailView;
import org.springframework.beans.factory.annotation.Autowired;

import static de.kkendzia.myintranet.ei.core.i18n.TranslationKeys.NAME;
import static de.kkendzia.myintranet.ei.core.i18n.TranslationKeys.SEARCH;
import static de.kkendzia.myintranet.ei.core.utils.GridColumnFactory.addCollapsedColumn;
import static de.kkendzia.myintranet.ei.core.utils.GridColumnFactory.addSpacerColumn;
import static de.kkendzia.myintranet.ei.ui.layout.EIDrawer.EIMenuKeys.MANDANTEN;
import static de.kkendzia.myintranet.ei.ui.views.mandant.routes.MandantRoutes.NAV_ROOT;
import static java.util.Objects.requireNonNull;

@Route(value = MandantSearchView.NAV, layout = EIMainLayout.class)
@MenuRoute(label = SEARCH, parent = MANDANTEN, position = 1)
public final class MandantSearchView extends AbstractEIView<SearchLayout<MandantSearchItem>>
{
    public static final String NAV = NAV_ROOT + "/search";

    private final MandantSearchPresenter presenter;

    @Autowired
    public MandantSearchView(MandantSearchPresenter presenter)
    {
        this.presenter = requireNonNull(presenter, "presenter can't be null!");

        SearchLayout<MandantSearchItem> root = getContent();
        root.setNavigationAction(new NavigateWithId<>(MandantDetailView.class, MandantSearchItem::id));

        Grid<MandantSearchItem> grid = root.getGrid();
        addCollapsedColumn(grid, getTranslation(NAME), MandantSearchItem::name);
        addSpacerColumn(grid);

        grid.setItems(presenter.getSearchDataProvider());
    }

    @Override
    public void beforeEnterView(BeforeEnterEvent event)
    {
        String searchtext = qpValues(SearchParameters.SEARCH_TEXT).findFirst().orElse("");

        SearchLayout<MandantSearchItem> layout = getContent();
        layout.setSearchText(searchtext);
        presenter.search(new MandantSearchFilters(searchtext));
    }
}
