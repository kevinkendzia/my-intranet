package de.kkendzia.myintranet.app.init.queries;

import de.kkendzia.myintranet.app._framework.cqrs.query.SingleResultQuery;

public record IsAppInit() implements SingleResultQuery<Boolean, Void>
{
    public interface IsAppInitHandler extends SingleResultQueryHandler<IsAppInit, Boolean, Void>
    {
        @Override
        default Class<IsAppInit> getQueryClass()
        {
            return IsAppInit.class;
        }
    }
}
