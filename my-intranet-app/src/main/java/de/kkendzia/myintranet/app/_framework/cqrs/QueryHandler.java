package de.kkendzia.myintranet.app._framework.cqrs;

import static java.util.Objects.requireNonNull;

public interface QueryHandler<Q extends QueryHandler.Query<R>, R>
{
    Class<Q> getQueryClass();
    R execute(Q query);


    interface Query<R>
    {
    }

    abstract class AbstractQueryHandler<Q extends Query<R>, R> implements QueryHandler<Q, R>
    {
        private final Class<Q> queryClass;

        protected AbstractQueryHandler(final Class<Q> queryClass)
        {
            this.queryClass = requireNonNull(queryClass, "queryClass can't be null!");
        }

        @Override
        public Class<Q> getQueryClass()
        {
            return queryClass;
        }
    }
}
