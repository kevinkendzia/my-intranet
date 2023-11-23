package de.kkendzia.myintranet.app.auth.queries;

import de.kkendzia.myintranet.app._framework.cqrs.QueryHandler;
import de.kkendzia.myintranet.app._framework.cqrs.QueryHandler.PagedQuery;
import de.kkendzia.myintranet.app._framework.cqrs.QueryHandler.PagedQueryHandler;
import de.kkendzia.myintranet.app._framework.cqrs.QueryHandler.Query;
import de.kkendzia.myintranet.app.auth.shared.AuthUser;

public record FindAuthUserByUsername(String username) implements PagedQuery<AuthUser, FindAuthUserByUsername.Failure>
{
    public interface FindAuthUserByUsernameHandler extends PagedQueryHandler<FindAuthUserByUsername, AuthUser, Failure>
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
