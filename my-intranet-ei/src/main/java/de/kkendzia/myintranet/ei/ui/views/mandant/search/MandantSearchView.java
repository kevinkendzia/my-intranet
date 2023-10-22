package de.kkendzia.myintranet.ei.ui.views.mandant.search;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.provider.ConfigurableFilterDataProvider;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.Route;
import de.kkendzia.myintranet.ei.core.search.SearchParameters;
import de.kkendzia.myintranet.ei.core.view.AbstractEIView;
import de.kkendzia.myintranet.ei.core.view.layouts.SearchLayout;
import de.kkendzia.myintranet.ei.core.view.layouts.SearchLayout.NavigationAction.NavigateWithId;
import de.kkendzia.myintranet.ei.ui.components.menu.provider.AnnotationItemProvider.MenuRoute;
import de.kkendzia.myintranet.ei.ui.layout.EIMainLayout;
import de.kkendzia.myintranet.ei.ui.views.mandant.detail.MandantDetailView;
import org.springframework.beans.factory.annotation.Autowired;

import static de.kkendzia.myintranet.ei.core.i18n.TranslationKeys.NAME;
import static de.kkendzia.myintranet.ei.core.i18n.TranslationKeys.SEARCH;
import static de.kkendzia.myintranet.ei.core.utils.GridColumnFactory.addCollapsedColumn;
import static de.kkendzia.myintranet.ei.core.utils.GridColumnFactory.addSpacerColumn;
import static de.kkendzia.myintranet.ei.ui.layout.EIDrawer.EIMenuKeys.MANDANTEN;
import static de.kkendzia.myintranet.ei.ui.views.mandant.routes.MandantRoutes.NAV_ROOT;

@Route(value = MandantSearchView.NAV, layout = EIMainLayout.class)
@MenuRoute(label = SEARCH, parent = MANDANTEN, position = 1)
public final class MandantSearchView extends AbstractEIView<SearchLayout<MandantSearchPresenter.SearchItem>>
{
    public static final String NAV = NAV_ROOT + "/search";

    private final ConfigurableFilterDataProvider<MandantSearchPresenter.SearchItem, Void, String> dataProvider;
    private final MandantSearchPresenter presenter;

    @Autowired
    public MandantSearchView(MandantSearchPresenter presenter)
    {
        this.presenter = presenter;

        SearchLayout<MandantSearchPresenter.SearchItem> root = getContent();
        root.setNavigationAction(new NavigateWithId<>(MandantDetailView.class, MandantSearchPresenter.SearchItem::id));

        Grid<MandantSearchPresenter.SearchItem> grid = root.getGrid();
        addCollapsedColumn(grid, getTranslation(NAME), MandantSearchPresenter.SearchItem::name);
        addSpacerColumn(grid);

        dataProvider = presenter.createSearchDataProvider().withConfigurableFilter();
        grid.setItems(dataProvider);
    }

    @Override
    public void beforeEnterView(BeforeEnterEvent event)
    {
        String searchtext = qpValues(SearchParameters.SEARCH_TEXT).findFirst().orElse("");

        SearchLayout<MandantSearchPresenter.SearchItem> layout = getContent();
        layout.setSearchText(searchtext);
        dataProvider.setFilter(searchtext);
    }
}
