package de.kkendzia.myintranet.ei.core.search;

import com.vaadin.flow.data.provider.ConfigurableFilterDataProvider;
import com.vaadin.flow.data.provider.DataProvider;
import de.kkendzia.myintranet.app.service._framework.SearchService;
import de.kkendzia.myintranet.ei.core.presenter.EISearchPresenter;

import static java.util.Objects.requireNonNull;

public abstract class DefaultSearchPresenter<T, F extends SearchService.SearchFilters>
        implements EISearchPresenter<T, F>
{
    protected final ConfigurableFilterDataProvider<T, Void, F> searchDataProvider;

    protected DefaultSearchPresenter(SearchService<T, F> searchService)
    {
        requireNonNull(searchService, "searchService can't be null!");
        searchDataProvider =
                DataProvider
                        .<T, F>fromFilteringCallbacks(
                                query -> searchService.fetch(mapQuery(query)),
                                query -> searchService.count(mapQuery(query)))
                        .withConfigurableFilter();
    }

    public ConfigurableFilterDataProvider<T, Void, F> getSearchDataProvider()
    {
        return searchDataProvider;
    }

    public void search(F mandantSearchFilters)
    {
        searchDataProvider.setFilter(mandantSearchFilters);
    }
}
