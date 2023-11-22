package de.kkendzia.myintranet.app.auth.queries;

import de.kkendzia.myintranet.app._framework.cqrs.QueryHandler;
import de.kkendzia.myintranet.app._framework.cqrs.QueryHandler.Query;
import de.kkendzia.myintranet.app.auth.shared.AuthUser;

public record FindAuthUserByUsername(String username) implements Query<AuthUser, FindAuthUserByUsername.Failure>
{
    public interface FindAuthUserByUsernameHandler extends QueryHandler<FindAuthUserByUsername, AuthUser, Failure>
    {
        default Class<FindAuthUserByUsername> getQueryClass()
        {
            return FindAuthUserByUsername.class;
        }
    }

    public enum Failure
    {
        NO_USER
    }
}
