package de.kkendzia.myintranet.ei.ui.views.admin.role.search;

import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.provider.SortDirection;
import de.kkendzia.myintranet.app._framework.SimpleSearchFilters;
import de.kkendzia.myintranet.app._framework.SimpleSearchItem;
import de.kkendzia.myintranet.app._framework.cqrs.QueryHandler;
import de.kkendzia.myintranet.app._framework.cqrs.QueryHandler.Paging;
import de.kkendzia.myintranet.app._framework.cqrs.QueryMediator;
import de.kkendzia.myintranet.app.search.queries.SearchRoles;
import de.kkendzia.myintranet.ei.core.presenter.Presenter;
import de.kkendzia.myintranet.ei.core.view.search.DefaultSearchPresenter;

@Presenter
public class AdminRoleSearchPresenter
        extends DefaultSearchPresenter<SimpleSearchItem, SimpleSearchFilters, SearchRoles, SearchRoles.ResultItem, SearchRoles.Failure>
{
    public AdminRoleSearchPresenter(QueryMediator quMediator)
    {
        super(quMediator);
    }

    @Override
    protected SimpleSearchItem mapItem(final SearchRoles.ResultItem source)
    {
        return new SimpleSearchItem(source.id(), source.name());
    }

    @Override
    protected SearchRoles mapQuery(final Query<SimpleSearchItem, SimpleSearchFilters> query)
    {
        return query.getFilter().map(f -> new SearchRoles(f.searchText())).orElseGet(() -> new SearchRoles(""));
    }

    @Override
    protected Paging<SearchRoles.ResultItem> mapPaging(final Query<SimpleSearchItem, SimpleSearchFilters> query)
    {
        return new Paging<>(
                query.getOffset(),
                query.getLimit(),
                query.getSortOrders()
                        .stream()
                        .map(x -> new QueryHandler.Order(
                                x.getSorted(),
                                x.getDirection() == SortDirection.ASCENDING
                                ? QueryHandler.Direction.ASC
                                : QueryHandler.Direction.DESC))
                        .toList());
    }

}
