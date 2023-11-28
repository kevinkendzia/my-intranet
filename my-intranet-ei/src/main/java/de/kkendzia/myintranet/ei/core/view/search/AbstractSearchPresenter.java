package de.kkendzia.myintranet.ei.core.view.search;

import com.vaadin.flow.data.provider.ConfigurableFilterDataProvider;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.Query;
import de.kkendzia.myintranet.app._framework.cqrs.query.QueryMediator;
import de.kkendzia.myintranet.app._framework.cqrs.query.paged.PagedQuery;
import de.kkendzia.myintranet.app._framework.cqrs.query.paged.Paging;
import de.kkendzia.myintranet.ei.core.presenter.EISearchPresenter;

import java.util.Optional;
import java.util.stream.Stream;

import static de.kkendzia.myintranet.ei.utils.QueryUtils.mapPaging;
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

    public ConfigurableFilterDataProvider<T, Void, PagedQuery<T, ?>> getSearchDataProvider()
    {
        return searchDataProvider;
    }

    public void search(PagedQuery<T, ?> mandantSearchFilters)
    {
        searchDataProvider.setFilter(mandantSearchFilters);
    }
}
