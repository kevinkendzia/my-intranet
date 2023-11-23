package de.kkendzia.myintranet.app._framework.cqrs.query;

import de.kkendzia.myintranet.app._framework.cqrs.query.QueryHandler.Query;
import de.kkendzia.myintranet.app._framework.cqrs.query.paged.PagedQuery;
import de.kkendzia.myintranet.app._framework.cqrs.query.paged.PagedQueryHandler;
import de.kkendzia.myintranet.app._framework.cqrs.query.paged.Paging;
import de.kkendzia.myintranet.app._framework.result.ListResult;
import de.kkendzia.myintranet.app._framework.result.SingleResult;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

import static java.util.Objects.requireNonNull;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@Service
public class QueryMediator
{
    private final Map<Class<? extends Query<?, ?>>, QueryHandler<?, ?, ?>> queryHandlerMap;

    public QueryMediator(final Set<QueryHandler<?, ?, ?>> queryHandlers)
    {
        requireNonNull(queryHandlers, "queryHandlers can't be null!");
        this.queryHandlerMap = queryHandlers.stream().collect(toMap(QueryHandler::getQueryClass, identity()));
    }

    public <Q extends Query<R, F>, R, F> long count(Q query)
    {
        return getHandler(query).count(query);
    }

    public <Q extends Query<R, F>, R, F> SingleResult<R, F> fetchOne(Q query)
    {
        return getHandler(query).fetchOne(query);
    }

    public <Q extends PagedQuery<R, F>, R, F> ListResult<R, F> fetchAll(Q query, Paging paging)
    {
        return getHandler(query).fetchAll(query, paging);
    }

    public <Q extends Query<R, F>, R, F> de.kkendzia.myintranet.app._framework.result.ListResult<R, F> fetchAll(Q query)
    {
        return getHandler(query).fetchAll(query);
    }

    @SuppressWarnings("unchecked")
    private <Q extends Query<R, F>, R, F> QueryHandler<Q, R, F> getHandler(final Q query)
    {
        return (QueryHandler<Q, R, F>) queryHandlerMap.get(query.getClass());
    }

    @SuppressWarnings("unchecked")
    private <Q extends PagedQuery<R, F>, R, F> PagedQueryHandler<Q, R, F> getHandler(final Q query)
    {
        return (PagedQueryHandler<Q, R, F>) queryHandlerMap.get(query.getClass());
    }
}
