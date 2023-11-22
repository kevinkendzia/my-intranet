package de.kkendzia.myintranet.ei.ui.views.admin.permission.search;

import com.vaadin.flow.data.provider.Query;
import de.kkendzia.myintranet.app._framework.SimpleSearchFilters;
import de.kkendzia.myintranet.app._framework.SimpleSearchItem;
import de.kkendzia.myintranet.app._framework.cqrs.QueryMediator;
import de.kkendzia.myintranet.app.search.queries.SearchPermissions;
import de.kkendzia.myintranet.ei.core.presenter.Presenter;
import de.kkendzia.myintranet.ei.core.view.search.DefaultSearchPresenter;

@Presenter
public class AdminPermissionSearchPresenter extends DefaultSearchPresenter<SimpleSearchItem, SimpleSearchFilters, SearchPermissions>
{
    public AdminPermissionSearchPresenter(QueryMediator quMediator)
    {
        super(quMediator);
    }

    @Override
    protected SearchPermissions mapQuery(final Query<SimpleSearchItem, SimpleSearchFilters> query)
    {
        return new SearchPermissions(query.getFilter().map(SimpleSearchFilters::searchText).orElse(null));
    }
}
