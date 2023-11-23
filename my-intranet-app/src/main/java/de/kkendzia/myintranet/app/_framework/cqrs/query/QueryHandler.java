package de.kkendzia.myintranet.app._framework.cqrs.query;

import de.kkendzia.myintranet.app._framework.result.ListResult;
import de.kkendzia.myintranet.app._framework.result.SingleResult;

public interface QueryHandler<Q extends QueryHandler.Query<R, F>, R, F>
{
    Class<Q> getQueryClass();

    default SingleResult<R, F> fetchOne(Q query)
    {
        return fetchAll(query).reduce(() -> new IllegalStateException("Found more then 1 item!"));
    }

    default long count(Q query)
    {
        return fetchAll(query).count();
    }

    ListResult<R, F> fetchAll(Q query);

    //region TYPES
    interface Query<R, F>
    {
    }
    //endregion
}
