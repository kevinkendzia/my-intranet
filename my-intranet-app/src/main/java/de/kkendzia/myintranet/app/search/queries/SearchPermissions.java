package de.kkendzia.myintranet.app.search.queries;

import de.kkendzia.myintranet.app._framework.cqrs.query.paged.PagedQuery;
import de.kkendzia.myintranet.domain.permission.Permission.PermissionID;

public record SearchPermissions(String searchtext)
        implements PagedQuery<SearchPermissions.ResultItem, SearchPermissions.Failure>
{
    public interface SearchPermissionsHandler
            extends QueryHandler<SearchPermissions, SearchPermissions.ResultItem, SearchPermissions.Failure>
    {
        @Override
        default Class<SearchPermissions> getQueryClass()
        {
            return SearchPermissions.class;
        }
    }

    public record ResultItem(
            PermissionID id,
            String name)
    {
        public String idString()
        {
            return id().toString();
        }
    }

    public enum Failure
    {
        UNKNOWN
    }
}
