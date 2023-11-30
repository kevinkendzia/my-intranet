package de.kkendzia.myintranet.ei.ui.views.admin.role.search;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.Route;
import de.kkendzia.myintranet.app.search.queries.SearchRoles;
import de.kkendzia.myintranet.app.search.queries.SearchRoles.ResultItem;
import de.kkendzia.myintranet.domain.permission.Permission;
import de.kkendzia.myintranet.ei.core.i18n.TranslationKeys;
import de.kkendzia.myintranet.ei._framework.view.AbstractEIView;
import de.kkendzia.myintranet.ei._framework.view.search.SearchParameters;
import de.kkendzia.myintranet.ei.ui.components.menu.provider.annotation.MenuRoute;
import de.kkendzia.myintranet.ei.ui.layouts.SearchLayout;
import de.kkendzia.myintranet.ei.ui.layouts.main.EIMainLayout;
import jakarta.annotation.security.RolesAllowed;

import static de.kkendzia.myintranet.ei.core.i18n.TranslationKeys.NAME;
import static de.kkendzia.myintranet.ei.ui.layouts.main.EIDrawer.EIMenuKeys.ROLE;
import static de.kkendzia.myintranet.ei.utils.GridColumnFactory.addCollapsedColumn;
import static de.kkendzia.myintranet.ei.utils.GridColumnFactory.addSpacerColumn;
import static java.util.Objects.requireNonNull;

@Route(value = AdminRoleSearchView.NAV, layout = EIMainLayout.class)
@MenuRoute(label = TranslationKeys.SEARCH, parent = ROLE)
@RolesAllowed(Permission.ROOT)
public class AdminRoleSearchView extends AbstractEIView<SearchLayout<ResultItem>>
{
    public static final String NAV = "admin/role";
    private final AdminRoleSearchPresenter presenter;

    public AdminRoleSearchView(AdminRoleSearchPresenter presenter)
    {
        this.presenter = requireNonNull(presenter, "presenter can't be null!");

        SearchLayout<ResultItem> root = getContent();
//        root.setNavigationAction(new NavigateWithId<>(MandantDetailView.class, SimpleSearchItem::id));

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
        presenter.search(new SearchRoles(searchtext));
    }
}
