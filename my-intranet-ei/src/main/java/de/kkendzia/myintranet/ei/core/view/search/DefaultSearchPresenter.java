package de.kkendzia.myintranet.ei.core.view.search;

import com.vaadin.flow.data.provider.ConfigurableFilterDataProvider;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.Query;
import de.kkendzia.myintranet.app._framework.cqrs.QueryHandler;
import de.kkendzia.myintranet.app._framework.cqrs.QueryHandler.Paging;
import de.kkendzia.myintranet.app._framework.cqrs.QueryMediator;
import de.kkendzia.myintranet.ei.core.presenter.EISearchPresenter;

import static java.util.Objects.requireNonNull;

public abstract class DefaultSearchPresenter<T, F, Q extends QueryHandler.Query<T2, F2>, T2, F2>
        implements EISearchPresenter<T, F>
{
    protected final ConfigurableFilterDataProvider<T, Void, F> searchDataProvider;

    protected DefaultSearchPresenter(QueryMediator quMediator)
    {
        requireNonNull(quMediator, "quMediator can't be null!");
        searchDataProvider =
                DataProvider
                        .<T, F>fromFilteringCallbacks(
                                query ->
                                {
                                    final Q q = mapQuery(query);
                                    final Paging<T2> tPaging = mapPaging(query);
                                    return quMediator.fetchAll(q, tPaging).stream().map(this::mapItem);
                                },
                                query -> Math.toIntExact(quMediator.count(mapQuery(query))))
                        .withConfigurableFilter();
    }

    protected abstract T mapItem(final T2 source);

    protected abstract Q mapQuery(Query<T, F> query);

    protected abstract Paging<T2> mapPaging(Query<T, F> query);

    public ConfigurableFilterDataProvider<T, Void, F> getSearchDataProvider()
    {
        return searchDataProvider;
    }

    public void search(F mandantSearchFilters)
    {
        searchDataProvider.setFilter(mandantSearchFilters);
    }
}
