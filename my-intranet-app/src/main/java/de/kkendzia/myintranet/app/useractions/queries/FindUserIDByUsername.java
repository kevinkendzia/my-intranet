package de.kkendzia.myintranet.app.useractions.queries;

import de.kkendzia.myintranet.app._framework.cqrs.query.QueryHandler;
import de.kkendzia.myintranet.app._framework.cqrs.query.QueryHandler.Query;
import de.kkendzia.myintranet.domain.user.EIUser.EIUserID;

public record FindUserIDByUsername(String userName) implements Query<EIUserID, FindUserIDByUsername.Failure>
{
    public interface FindUserIDByUsernameHandler extends QueryHandler<FindUserIDByUsername, EIUserID, Failure>
    {
        @Override
        default Class<FindUserIDByUsername> getQueryClass()
        {
            return FindUserIDByUsername.class;
        }
    }

    public enum Failure
    {
        UNKNOWN
    }
}
