package de.kkendzia.myintranet.ei.core.presenter;

import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.provider.SortDirection;
import de.kkendzia.myintranet.app.service._framework.SearchService;
import de.kkendzia.myintranet.domain._framework.dao.Paging;

public interface EISearchPresenter<T, F extends SearchService.SearchFilters> extends EIPresenter
{
    default SearchService.SearchQuery<F> mapQuery(Query<T, F> query)
    {
        return new SearchService.SearchQuery<>(
                query.getFilter().orElse(null),
                new Paging(
                        query.getOffset(),
                        query.getLimit(),
                        query.getSortOrders()
                                .stream()
                                .map(s -> new Paging.SortOrder(
                                        s.getSorted(),
                                        s.getDirection() == SortDirection.ASCENDING))
                                .toList()));
    }
}
