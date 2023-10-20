package de.kkendzia.myintranet.ei.ui.views.other.mandant.search;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.provider.ConfigurableFilterDataProvider;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.Route;
import de.kkendzia.myintranet.ei.core.search.SearchParameters;
import de.kkendzia.myintranet.ei.core.view.AbstractEIView;
import de.kkendzia.myintranet.ei.core.view.SearchLayout;
import de.kkendzia.myintranet.ei.core.view.SearchLayout.NavigationAction.NavigateWithId;
import de.kkendzia.myintranet.ei.ui.components.menu.provider.AnnotationItemProvider.MenuRoute;
import de.kkendzia.myintranet.ei.ui.layout.EIMainLayout;
import de.kkendzia.myintranet.ei.ui.layout.EIMenu;
import de.kkendzia.myintranet.ei.ui.views.other.mandant.detail.MandantDetailView;
import de.kkendzia.myintranet.ei.ui.views.other.mandant.search.MandantSearchPresenter.SearchItem;
import org.springframework.beans.factory.annotation.Autowired;

import static de.kkendzia.myintranet.ei.core.i18n.TranslationKeys.NAME;
import static de.kkendzia.myintranet.ei.core.utils.GridColumnFactory.addCollapsedColumn;
import static de.kkendzia.myintranet.ei.core.utils.GridColumnFactory.addSpacerColumn;

@Route(value = "mandant/search", layout = EIMainLayout.class)
@MenuRoute(label = "menu.mandant", parent = EIMenu.PARENT_OTHER)
public class MandantSearchView extends AbstractEIView<SearchLayout<SearchItem>>
{
    private final ConfigurableFilterDataProvider<SearchItem, Void, String> dataProvider;
    private MandantSearchPresenter presenter;

    @Autowired
    public MandantSearchView(MandantSearchPresenter presenter)
    {
        this.presenter = presenter;

        SearchLayout<SearchItem> root = getContent();
        root.setNavigationAction(new NavigateWithId<>(MandantDetailView.class, SearchItem::id));

        Grid<SearchItem> grid = root.getGrid();
        addCollapsedColumn(grid, getTranslation(NAME), SearchItem::name);
        addSpacerColumn(grid);

        dataProvider = presenter.createSearchDataProvider().withConfigurableFilter();
        grid.setItems(dataProvider);
    }

    @Override
    public void beforeEnterView(BeforeEnterEvent event)
    {
        String searchtext = qpValues(SearchParameters.SEARCH_TEXT).findFirst().orElse("");

        SearchLayout<SearchItem> layout = getContent();
        layout.setSearchText(searchtext);
        dataProvider.setFilter(searchtext);
    }
}
