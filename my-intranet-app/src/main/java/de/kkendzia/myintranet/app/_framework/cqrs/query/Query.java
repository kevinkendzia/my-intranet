package de.kkendzia.myintranet.app._framework.cqrs.query;

import de.kkendzia.myintranet.app._framework.cqrs.query.ListQuery.ListQueryHandler;
import de.kkendzia.myintranet.app._framework.cqrs.query.SingleResultQuery.SingleResultQueryHandler;

import java.io.Serializable;

//region TYPES
public sealed interface Query<R, F> extends Serializable permits ListQuery, SingleResultQuery
{
    sealed interface QueryHandler<Q extends Query<R, F>, R, F> permits ListQueryHandler, SingleResultQueryHandler
    {
        Class<Q> getQueryClass();
    }
}
