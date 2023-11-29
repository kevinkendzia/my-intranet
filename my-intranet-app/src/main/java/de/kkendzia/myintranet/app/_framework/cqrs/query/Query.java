package de.kkendzia.myintranet.app._framework.cqrs.query;

import java.io.Serializable;

//region TYPES
public interface Query<R, F> extends Serializable
{
    interface QueryHandler<Q extends Query<R, F>, R, F>
    {
        Class<Q> getQueryClass();
    }
}
