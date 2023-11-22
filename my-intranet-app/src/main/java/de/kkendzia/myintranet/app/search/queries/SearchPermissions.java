package de.kkendzia.myintranet.app.search.queries;

import de.kkendzia.myintranet.app._framework.cqrs.QueryHandler;
import de.kkendzia.myintranet.app._framework.cqrs.QueryHandler.Query;

public record SearchPermissions(String searchtext) implements Query<SearchPermissions.ResultItem, SearchPermissions.Failure>
{
    interface SearchPermissionsHandler extends QueryHandler<SearchPermissions, SearchPermissions.ResultItem, SearchPermissions.Failure>
    {
        @Override
        default Class<SearchPermissions> getQueryClass()
        {
            return SearchPermissions.class;
        }
    }

    record ResultItem(
            long id,
            String name)
    {
        // just a record
    }

    public enum Failure
    {
        UNKNOWN
    }
}
