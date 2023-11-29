package de.kkendzia.myintranet.app._framework.cqrs.query;

import de.kkendzia.myintranet.app._framework.result.ListResult;

public interface ListQuery<R, F> extends Query<R, F>
{
    interface ListQueryHandler<Q extends ListQuery<R, F>, R, F> extends QueryHandler<Q, R, F>
    {
        default long count(Q query)
        {
            return fetchAll(query).count();
        }

        ListResult<R, F> fetchAll(Q query);
    }
}
