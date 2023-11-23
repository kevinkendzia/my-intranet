package de.kkendzia.myintranet.ei.ui.views.admin.user.search;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.Route;
import de.kkendzia.myintranet.app.search.queries.SearchEIUsers;
import de.kkendzia.myintranet.app.search.queries.SearchEIUsers.ResultItem;
import de.kkendzia.myintranet.domain.permission.Permission;
import de.kkendzia.myintranet.ei.core.i18n.TranslationKeys;
import de.kkendzia.myintranet.ei.core.view.search.SearchParameters;
import de.kkendzia.myintranet.ei.core.view.AbstractEIView;
import de.kkendzia.myintranet.ei.ui.layouts.SearchLayout;
import de.kkendzia.myintranet.ei.ui.layouts.SearchLayout.NavigationAction.NavigateWithId;
import de.kkendzia.myintranet.ei.ui.components.menu.provider.annotation.MenuRoute;
import de.kkendzia.myintranet.ei.ui.layouts.main.EIMainLayout;
import de.kkendzia.myintranet.ei.ui.views.mandant.detail.MandantDetailView;
import jakarta.annotation.security.RolesAllowed;

import static de.kkendzia.myintranet.ei.core.i18n.TranslationKeys.NAME;
import static de.kkendzia.myintranet.ei.core.utils.GridColumnFactory.addCollapsedColumn;
import static de.kkendzia.myintranet.ei.core.utils.GridColumnFactory.addSpacerColumn;
import static de.kkendzia.myintranet.ei.ui.layouts.main.EIDrawer.EIMenuKeys.USER;
import static java.util.Objects.requireNonNull;

@Route(value = AdminUserSearchView.NAV, layout = EIMainLayout.class)
@MenuRoute(label = TranslationKeys.SEARCH, parent = USER)
@RolesAllowed(Permission.ROOT)
public class AdminUserSearchView extends AbstractEIView<SearchLayout<ResultItem>>
{
    public static final String NAV = "admin/user";
    private final AdminUserSearchPresenter presenter;

    public AdminUserSearchView(AdminUserSearchPresenter presenter)
    {
        this.presenter = requireNonNull(presenter, "presenter can't be null!");

        SearchLayout<ResultItem> root = getContent();
        root.setNavigationAction(new NavigateWithId<>(MandantDetailView.class, ResultItem::id));

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
        presenter.search(new SearchEIUsers(searchtext));
    }
}
