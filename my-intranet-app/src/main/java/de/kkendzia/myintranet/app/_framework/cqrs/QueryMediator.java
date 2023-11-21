package de.kkendzia.myintranet.app._framework.cqrs;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

import static java.util.Objects.requireNonNull;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@Service
public class QueryMediator
{
    private final Map<Class<? extends QueryHandler.Query<?>>, QueryHandler<?, ?>> queryHandlerMap;

    public QueryMediator(final Set<QueryHandler<?, ?>> queryHandlers)
    {
        requireNonNull(queryHandlers, "queryHandlers can't be null!");
        this.queryHandlerMap = queryHandlers.stream().collect(toMap(QueryHandler::getQueryClass, identity()));
    }

    @SuppressWarnings("unchecked")
    public <Q extends QueryHandler.Query<R>, R> R execute(Q query)
    {
        final QueryHandler<Q, R> handler = (QueryHandler<Q, R>) queryHandlerMap.get(query.getClass());
        return handler.execute(query);
    }
}
