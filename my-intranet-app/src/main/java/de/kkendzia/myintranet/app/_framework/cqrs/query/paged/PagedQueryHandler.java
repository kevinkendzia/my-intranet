package de.kkendzia.myintranet.app._framework.cqrs.query.paged;

import de.kkendzia.myintranet.app._framework.cqrs.query.QueryHandler;
import de.kkendzia.myintranet.app._framework.result.ListResult;

public interface PagedQueryHandler<Q extends PagedQuery<R, F>, R, F> extends QueryHandler<Q, R, F>
{
    ListResult<R, F> fetchAll(Q query, Paging paging);

    default ListResult<R, F> fetchAll(Q query)
    {
        return fetchAll(query, null);
    }
}
