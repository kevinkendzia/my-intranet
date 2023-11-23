package de.kkendzia.myintranet.ei.core.view.search;

import com.vaadin.flow.data.provider.*;
import de.kkendzia.myintranet.app._framework.cqrs.QueryHandler;
import de.kkendzia.myintranet.app._framework.cqrs.QueryHandler.Paging;
import de.kkendzia.myintranet.app._framework.cqrs.QueryMediator;
import de.kkendzia.myintranet.ei.core.presenter.EISearchPresenter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

public abstract class AbstractSearchPresenter<T> implements EISearchPresenter<T>
{
    private final ConfigurableFilterDataProvider<T, Void, QueryHandler.Query<T, ?>> searchDataProvider;
    private final QueryHandler.Query<T, ?> defaultQuery;

    protected AbstractSearchPresenter(QueryMediator quMediator, QueryHandler.Query<T, ?> defaultQuery)
    {
        requireNonNull(quMediator, "quMediator can't be null!");
        this.defaultQuery = defaultQuery;

        searchDataProvider =
                DataProvider
                        .<T, QueryHandler.Query<T, ?>>fromFilteringCallbacks(
                                query -> fetch(quMediator, query),
                                query -> count(quMediator, query))
                        .withConfigurableFilter();
    }

    protected AbstractSearchPresenter(QueryMediator quMediator)
    {
        this(quMediator, null);
    }

    protected Optional<QueryHandler.Query<T, ?>> getDefaultQuery()
    {
        return Optional.ofNullable(defaultQuery);
    }

    private Stream<T> fetch(final QueryMediator quMediator, final Query<T, QueryHandler.Query<T, ?>> query)
    {
        return query
                .getFilter()
                .or(this::getDefaultQuery)
                .map(q ->
                {
                    final Paging tPaging = mapPaging(query);
                    return quMediator.fetchAll2(q, tPaging).stream();
                })
                .orElseGet(Stream::empty);
    }

    private int count(final QueryMediator quMediator, final Query<T, QueryHandler.Query<T, ?>> query)
    {
        return query
                .getFilter()
                .or(this::getDefaultQuery)
                .map(q -> Math.toIntExact(quMediator.count(q)))
                .orElse(0);
    }


    protected Paging mapPaging(Query<T, QueryHandler.Query<T, ?>> query)
    {
        return new Paging(
                query.getOffset(),
                query.getLimit(),
                mapSortOrders(query.getSortOrders()));
    }

    protected List<QueryHandler.Order> mapSortOrders(final List<QuerySortOrder> sortOrders)
    {
        return sortOrders
                .stream()
                .map(x -> new QueryHandler.Order(
                        x.getSorted(),
                        x.getDirection() == SortDirection.ASCENDING
                        ? QueryHandler.Direction.ASC
                        : QueryHandler.Direction.DESC))
                .toList();
    }

    public ConfigurableFilterDataProvider<T, Void, QueryHandler.Query<T, ?>> getSearchDataProvider()
    {
        return searchDataProvider;
    }

    public void search(QueryHandler.Query<T, ?> mandantSearchFilters)
    {
        searchDataProvider.setFilter(mandantSearchFilters);
    }
}
