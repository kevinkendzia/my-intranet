package de.kkendzia.myintranet.app.search.queries;

import de.kkendzia.myintranet.app._framework.cqrs.query.paged.PagedQuery;
import de.kkendzia.myintranet.app._shared.failures.SearchFailure;
import de.kkendzia.myintranet.domain.role.Role.RoleID;

public record SearchRoles(String searchtext) implements PagedQuery<SearchRoles.ResultItem, SearchFailure>
{
    public interface SearchRolesHandler extends PagedQueryHandler<SearchRoles, SearchRoles.ResultItem, SearchFailure>
    {
        @Override
        default Class<SearchRoles> getQueryClass()
        {
            return SearchRoles.class;
        }
    }

    public record ResultItem(
            RoleID id,
            String name)
    {
        // just a record
    }
}
