package de.kkendzia.myintranet.app.init.queries;

import de.kkendzia.myintranet.app._framework.cqrs.query.QueryHandler;

public record IsAppInit() implements QueryHandler.Query<Boolean, Void>
{
    public interface IsAppInitHandler extends QueryHandler<IsAppInit, Boolean, Void>
    {
        @Override
        default Class<IsAppInit> getQueryClass()
        {
            return IsAppInit.class;
        }
    }
}
