package de.kkendzia.myintranet.app._framework.cqrs.query;

import de.kkendzia.myintranet.app._framework.result.SingleResult;

//region TYPES
public non-sealed interface SingleResultQuery<R, F> extends Query<R, F>
{
    non-sealed interface SingleResultQueryHandler<Q extends SingleResultQuery<R, F>, R, F> extends QueryHandler<Q, R, F>
    {
        SingleResult<R, F> fetchOne(Q query);
    }
}
