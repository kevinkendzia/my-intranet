package de.kkendzia.myintranet.app._framework.cqrs.query.paged;

import de.kkendzia.myintranet.app._framework.cqrs.query.ListQuery;
import de.kkendzia.myintranet.app._framework.result.ListResult;

public interface PagedQuery<R, F> extends ListQuery<R, F>
{
    interface PagedQueryHandler<Q extends PagedQuery<R, F>, R, F> extends ListQueryHandler<Q, R, F>
    {
        ListResult<R, F> fetchAll(Q query, Paging paging);

        @Override
        default ListResult<R, F> fetchAll(Q query)
        {
            return fetchAll(query, null);
        }
    }
}
