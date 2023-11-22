package de.kkendzia.myintranet.app.auth.queries;

import de.kkendzia.myintranet.app._framework.cqrs.QueryHandler;
import de.kkendzia.myintranet.app._framework.cqrs.QueryHandler.Query;
import de.kkendzia.myintranet.app.auth.shared.AuthAuthority;
import de.kkendzia.myintranet.domain.user.EIUser;
import de.kkendzia.myintranet.domain.user.EIUser.EIUserID;

import java.util.Set;

public record FindAuthAuthorities(EIUserID userId) implements Query<AuthAuthority, FindAuthAuthorities.Failure>
{
    public interface FindAuthAuthoritiesHandler extends QueryHandler<FindAuthAuthorities, AuthAuthority, Failure>
    {
        default Class<FindAuthAuthorities> getQueryClass()
        {
            return FindAuthAuthorities.class;
        }
    }

    public enum Failure
    {
        NO_USER,
        UNKNOWN
    }
}
