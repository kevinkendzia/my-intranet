package de.kkendzia.myintranet.ei.core.view.search;

import com.vaadin.flow.data.provider.*;
import de.kkendzia.myintranet.app._framework.cqrs.query.QueryMediator;
import de.kkendzia.myintranet.app._framework.cqrs.query.paged.Direction;
import de.kkendzia.myintranet.app._framework.cqrs.query.paged.Order;
import de.kkendzia.myintranet.app._framework.cqrs.query.paged.PagedQuery;
import de.kkendzia.myintranet.app._framework.cqrs.query.paged.Paging;
import de.kkendzia.myintranet.ei.core.presenter.EISearchPresenter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

public abstract class AbstractSearchPresenter<T> implements EISearchPresenter<T>
{
    private final ConfigurableFilterDataProvider<T, Void, PagedQuery<T, ?>> searchDataProvider;
    private final PagedQuery<T, ?> defaultQuery;

    protected AbstractSearchPresenter(QueryMediator quMediator, PagedQuery<T, ?> defaultQuery)
    {
        requireNonNull(quMediator, "quMediator can't be null!");
        this.defaultQuery = defaultQuery;

        searchDataProvider =
                DataProvider
                        .<T, PagedQuery<T, ?>>fromFilteringCallbacks(
                                query -> fetch(quMediator, query),
                                query -> count(quMediator, query))
                        .withConfigurableFilter();
    }

    protected AbstractSearchPresenter(QueryMediator quMediator)
    {
        this(quMediator, null);
    }

    protected Optional<PagedQuery<T, ?>> getDefaultQuery()
    {
        return Optional.ofNullable(defaultQuery);
    }

    private Stream<T> fetch(final QueryMediator quMediator, final Query<T, PagedQuery<T, ?>> query)
    {
        return query
                .getFilter()
                .or(this::getDefaultQuery)
                .map(q ->
                {
                    final Paging tPaging = mapPaging(query);
                    return quMediator.fetchAll(q, tPaging).stream();
                })
                .orElseGet(Stream::empty);
    }

    private int count(final QueryMediator quMediator, final Query<T, PagedQuery<T, ?>> query)
    {
        return query
                .getFilter()
                .or(this::getDefaultQuery)
                .map(q -> Math.toIntExact(quMediator.count(q)))
                .orElse(0);
    }


    protected Paging mapPaging(Query<T, PagedQuery<T, ?>> query)
    {
        return new Paging(
                query.getOffset(),
                query.getLimit(),
                mapSortOrders(query.getSortOrders()));
    }

    protected List<Order> mapSortOrders(final List<QuerySortOrder> sortOrders)
    {
        return sortOrders
                .stream()
                .map(x -> new Order(
                        x.getSorted(),
                        x.getDirection() == SortDirection.ASCENDING
                        ? Direction.ASC
                        : Direction.DESC))
                .toList();
    }

    public ConfigurableFilterDataProvider<T, Void, PagedQuery<T, ?>> getSearchDataProvider()
    {
        return searchDataProvider;
    }

    public void search(PagedQuery<T, ?> mandantSearchFilters)
    {
        searchDataProvider.setFilter(mandantSearchFilters);
    }
}
