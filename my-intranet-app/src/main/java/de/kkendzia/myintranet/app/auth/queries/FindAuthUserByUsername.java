package de.kkendzia.myintranet.app.auth.queries;

import de.kkendzia.myintranet.app._framework.cqrs.query.SingleResultQuery;
import de.kkendzia.myintranet.app.auth.shared.AuthUser;

public record FindAuthUserByUsername(String username)
        implements SingleResultQuery<AuthUser, FindAuthUserByUsername.Failure>
{
    public interface FindAuthUserByUsernameHandler
            extends SingleResultQueryHandler<FindAuthUserByUsername, AuthUser, Failure>
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
