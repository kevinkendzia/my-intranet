package de.kkendzia.myintranet.app.auth.queries;

import de.kkendzia.myintranet.app._framework.cqrs.query.ListQuery;
import de.kkendzia.myintranet.app.auth.shared.AuthAuthority;
import de.kkendzia.myintranet.domain.user.EIUser.EIUserID;

public record FindAuthAuthorities(EIUserID userId) implements ListQuery<AuthAuthority, FindAuthAuthorities.Failure>
{
    public interface FindAuthAuthoritiesHandler extends ListQueryHandler<FindAuthAuthorities, AuthAuthority, Failure>
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
