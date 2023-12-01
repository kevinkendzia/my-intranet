package de.kkendzia.myintranet.ei.ui.tools.data;

import com.vaadin.flow.data.provider.AbstractBackEndDataProvider;
import com.vaadin.flow.data.provider.Query;
import de.kkendzia.myintranet.app._framework.cqrs.query.QueryMediator;
import de.kkendzia.myintranet.app._framework.cqrs.query.paged.PagedQuery;
import de.kkendzia.myintranet.app._framework.cqrs.query.paged.Paging;

import java.util.Optional;
import java.util.stream.Stream;

import static de.kkendzia.myintranet.ei.utils.QueryUtils.mapPaging;
import static java.util.Objects.requireNonNull;

public class QueryDataProvider<R> extends AbstractBackEndDataProvider<R, PagedQuery<R, ?>>
{
    private final transient QueryMediator quMediator;
    private final PagedQuery<R, ?> defaultQuery;

    public QueryDataProvider(final QueryMediator quMediator, PagedQuery<R, ?> defaultQuery)
    {
        this.quMediator = requireNonNull(quMediator, "quMediator can't be null!");
        this.defaultQuery = defaultQuery;
    }

    public QueryDataProvider(final QueryMediator quMediator)
    {
        this(quMediator, null);
    }

    @Override
    protected Stream<R> fetchFromBackEnd(final Query<R, PagedQuery<R, ?>> query)
    {
        return query
                .getFilter()
                .or(this::getOptionalDefaultQuery)
                .map(q ->
                {
                    final Paging tPaging = mapPaging(query);
                    return quMediator.fetchAll(q, tPaging).stream();
                })
                .orElseGet(Stream::empty);
    }

    @Override
    protected int sizeInBackEnd(final Query<R, PagedQuery<R, ?>> query)
    {
        return query
                .getFilter()
                .or(this::getOptionalDefaultQuery)
                .map(q -> Math.toIntExact(quMediator.count(q)))
                .orElse(0);
    }

    private Optional<PagedQuery<R, ?>> getOptionalDefaultQuery()
    {
        return Optional.ofNullable(defaultQuery);
    }
}
