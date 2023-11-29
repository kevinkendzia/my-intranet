package de.kkendzia.myintranet.app._framework.cqrs.query;

import de.kkendzia.myintranet.app._framework.cqrs.query.ListQuery.ListQueryHandler;
import de.kkendzia.myintranet.app._framework.cqrs.query.Query.QueryHandler;
import de.kkendzia.myintranet.app._framework.cqrs.query.SingleResultQuery.SingleResultQueryHandler;
import de.kkendzia.myintranet.app._framework.cqrs.query.paged.PagedQuery;
import de.kkendzia.myintranet.app._framework.cqrs.query.paged.PagedQuery.PagedQueryHandler;
import de.kkendzia.myintranet.app._framework.cqrs.query.paged.Paging;
import de.kkendzia.myintranet.app._framework.result.ListResult;
import de.kkendzia.myintranet.app._framework.result.SingleResult;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static java.util.Objects.requireNonNull;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@Service
public class QueryMediator
{
    public static final String EXCEPTION_MESSAGE_FORMAT = "QueryHandler for \"%s\" wasn't implemented yet!";
    private final Map<Class<? extends Query<?, ?>>, QueryHandler<?, ?, ?>> queryHandlerMap;

    public QueryMediator(final Set<? extends QueryHandler<?, ?, ?>> queryHandlers)
    {
        requireNonNull(queryHandlers, "queryHandlers can't be null!");
        this.queryHandlerMap = queryHandlers.stream().collect(toMap(QueryHandler::getQueryClass, identity()));
    }

    public <Q extends SingleResultQuery<R, F>, R, F> SingleResult<R, F> fetchOne(Q query)
    {
        return getSingleHandler(query).fetchOne(query);
    }

    public <Q extends SingleResultQuery<Boolean, F>, F> boolean test(Q query)
    {
        return fetchOne(query).getData();
    }

    @SuppressWarnings("unchecked")
    private <Q extends SingleResultQuery<R, F>, R, F> SingleResultQueryHandler<Q, R, F> getSingleHandler(final Q query)
    {
        return (SingleResultQueryHandler<Q, R, F>) Optional
                .ofNullable(queryHandlerMap.get(query.getClass()))
                .orElseThrow(() -> new IllegalStateException(EXCEPTION_MESSAGE_FORMAT.formatted(query.getClass())));
    }

    public <Q extends ListQuery<R, F>, R, F> long count(Q query)
    {
        return getListHandler(query).count(query);
    }

    public <Q extends PagedQuery<R, F>, R, F> ListResult<R, F> fetchAll(Q query, Paging paging)
    {
        return getPagedHandler(query).fetchAll(query, paging);
    }

    @SuppressWarnings("unchecked")
    private <Q extends PagedQuery<R, F>, R, F> PagedQueryHandler<Q, R, F> getPagedHandler(final Q query)
    {
        return (PagedQueryHandler<Q, R, F>) Optional
                .ofNullable(queryHandlerMap.get(query.getClass()))
                .orElseThrow(() -> new IllegalStateException(EXCEPTION_MESSAGE_FORMAT.formatted(query.getClass())));
    }

    public <Q extends ListQuery<R, F>, R, F> ListResult<R, F> fetchAll(Q query)
    {
        return getListHandler(query).fetchAll(query);
    }

    @SuppressWarnings("unchecked")
    private <Q extends ListQuery<R, F>, R, F> ListQueryHandler<Q, R, F> getListHandler(final Q query)
    {
        return (ListQueryHandler<Q, R, F>) Optional
                .ofNullable(queryHandlerMap.get(query.getClass()))
                .orElseThrow(() -> new IllegalStateException(EXCEPTION_MESSAGE_FORMAT.formatted(query.getClass())));
    }
}
