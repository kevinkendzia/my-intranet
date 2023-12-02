package de.kkendzia.myintranet.ei.ui.views.admin.permission.search;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.Route;
import de.kkendzia.myintranet.app.search.queries.SearchPermissions;
import de.kkendzia.myintranet.app.search.queries.SearchPermissions.ResultItem;
import de.kkendzia.myintranet.domain.permission.Permission;
import de.kkendzia.myintranet.ei._framework.view.AbstractEIView;
import de.kkendzia.myintranet.ei._framework.view.search.SearchParameters;
import de.kkendzia.myintranet.ei.core.i18n.TranslationKeys;
import de.kkendzia.myintranet.ei.core.navigation.NavigateWithItem;
import de.kkendzia.myintranet.ei.ui.layouts.SearchLayout;
import de.kkendzia.myintranet.ei.ui.layouts.main.EIMainLayout;
import de.kkendzia.myintranet.ei.ui.layouts.main.drawer.EIDrawer;
import de.kkendzia.myintranet.ei.ui.layouts.main.drawer.menu.provider.annotation.MenuRoute;
import de.kkendzia.myintranet.ei.ui.views.mandant.detail.MandantDetailView;
import jakarta.annotation.security.RolesAllowed;

import static de.kkendzia.myintranet.ei.core.i18n.TranslationKeys.NAME;
import static de.kkendzia.myintranet.ei.utils.GridColumnFactory.addCollapsedColumn;
import static de.kkendzia.myintranet.ei.utils.GridColumnFactory.addSpacerColumn;
import static java.util.Objects.requireNonNull;

@Route(value = AdminPermissionSearchView.NAV, layout = EIMainLayout.class)
@MenuRoute(label = TranslationKeys.SEARCH, parent = EIDrawer.EIMenuKeys.PERMISSION)
@RolesAllowed(Permission.ROOT)
public class AdminPermissionSearchView extends AbstractEIView<SearchLayout<ResultItem>>
{
    public static final String NAV = "admin/permission";
    private final AdminPermissionSearchPresenter presenter;

    public AdminPermissionSearchView(AdminPermissionSearchPresenter presenter)
    {
        this.presenter = requireNonNull(presenter, "presenter can't be null!");

        SearchLayout<ResultItem> root = getContent();
        root.setNavigationAction(NavigateWithItem.to(MandantDetailView.class, ResultItem::idString));

        Grid<ResultItem> grid = root.getGrid();
        addCollapsedColumn(grid, getTranslation(NAME), ResultItem::name);
        addSpacerColumn(grid);

        grid.setItems(presenter.getSearchDataProvider());
    }

    @Override
    protected void beforeEnterView(BeforeEnterEvent event)
    {
        String searchtext = qpValues(SearchParameters.SEARCH_TEXT).findFirst().orElse("");

        SearchLayout<ResultItem> layout = getContent();
        layout.setSearchText(searchtext);
        presenter.search(new SearchPermissions(searchtext));
    }
}
