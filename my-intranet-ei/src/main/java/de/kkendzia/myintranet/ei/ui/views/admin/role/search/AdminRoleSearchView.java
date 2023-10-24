package de.kkendzia.myintranet.ei.ui.views.admin.role.search;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.Route;
import de.kkendzia.myintranet.app.service._framework.SimpleSearchFilters;
import de.kkendzia.myintranet.app.service._framework.SimpleSearchItem;
import de.kkendzia.myintranet.domain.user.Permission;
import de.kkendzia.myintranet.ei.core.i18n.TranslationKeys;
import de.kkendzia.myintranet.ei.core.search.SearchParameters;
import de.kkendzia.myintranet.ei.core.view.AbstractEIView;
import de.kkendzia.myintranet.ei.core.view.layouts.SearchLayout;
import de.kkendzia.myintranet.ei.core.view.layouts.SearchLayout.NavigationAction.NavigateWithId;
import de.kkendzia.myintranet.ei.ui.components.menu.provider.AnnotationItemProvider.MenuRoute;
import de.kkendzia.myintranet.ei.ui.layout.EIMainLayout;
import de.kkendzia.myintranet.ei.ui.views.mandant.detail.MandantDetailView;
import jakarta.annotation.security.RolesAllowed;

import static de.kkendzia.myintranet.ei.core.i18n.TranslationKeys.NAME;
import static de.kkendzia.myintranet.ei.core.utils.GridColumnFactory.addCollapsedColumn;
import static de.kkendzia.myintranet.ei.core.utils.GridColumnFactory.addSpacerColumn;
import static de.kkendzia.myintranet.ei.ui.layout.EIDrawer.EIMenuKeys.ROLE;
import static java.util.Objects.requireNonNull;

@Route(value = AdminRoleSearchView.NAV, layout = EIMainLayout.class)
@MenuRoute(label = TranslationKeys.SEARCH, parent = ROLE)
@RolesAllowed(Permission.ROOT)
public class AdminRoleSearchView extends AbstractEIView<SearchLayout<SimpleSearchItem>>
{
    public static final String NAV = "admin/role";
    private final AdminRoleSearchPresenter presenter;
    public AdminRoleSearchView(AdminRoleSearchPresenter presenter)
    {
        this.presenter = requireNonNull(presenter, "presenter can't be null!");

        SearchLayout<SimpleSearchItem> root = getContent();
        root.setNavigationAction(new NavigateWithId<>(MandantDetailView.class, SimpleSearchItem::id));

        Grid<SimpleSearchItem> grid = root.getGrid();
        addCollapsedColumn(grid, getTranslation(NAME), SimpleSearchItem::name);
        addSpacerColumn(grid);

        grid.setItems(presenter.getSearchDataProvider());
    }

    @Override
    protected void beforeEnterView(BeforeEnterEvent event)
    {
        String searchtext = qpValues(SearchParameters.SEARCH_TEXT).findFirst().orElse("");

        SearchLayout<SimpleSearchItem> layout = getContent();
        layout.setSearchText(searchtext);
        presenter.search(new SimpleSearchFilters(searchtext));
    }
}
