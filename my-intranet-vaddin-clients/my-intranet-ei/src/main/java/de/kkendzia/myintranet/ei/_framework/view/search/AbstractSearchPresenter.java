package de.kkendzia.myintranet.ei._framework.view.search;

import com.vaadin.flow.data.provider.ConfigurableFilterDataProvider;
import de.kkendzia.myintranet.app._framework.cqrs.query.QueryMediator;
import de.kkendzia.myintranet.app._framework.cqrs.query.paged.PagedQuery;
import de.kkendzia.myintranet.ei._framework.presenter.EISearchPresenter;
import de.kkendzia.myintranet.ei.ui.tools.data.QueryDataProvider;

import static java.util.Objects.requireNonNull;

public abstract class AbstractSearchPresenter<T> implements EISearchPresenter<T>
{
    private final ConfigurableFilterDataProvider<T, Void, PagedQuery<T, ?>> searchDataProvider;

    protected AbstractSearchPresenter(QueryMediator quMediator)
    {
        requireNonNull(quMediator, "quMediator can't be null!");
        this.searchDataProvider = new QueryDataProvider<T>(quMediator).withConfigurableFilter();
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
