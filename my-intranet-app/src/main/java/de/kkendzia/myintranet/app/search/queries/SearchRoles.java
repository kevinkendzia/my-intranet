package de.kkendzia.myintranet.app.search.queries;

import de.kkendzia.myintranet.app._framework.cqrs.QueryHandler;
import de.kkendzia.myintranet.app._framework.cqrs.QueryHandler.Query;

public record SearchRoles(String searchtext) implements Query<SearchRoles.ResultItem, SearchRoles.Failure>
{
    public interface SearchRolesHandler extends QueryHandler<SearchRoles, SearchRoles.ResultItem, SearchRoles.Failure>
    {
        @Override
        default Class<SearchRoles> getQueryClass()
        {
            return SearchRoles.class;
        }
    }

    public record ResultItem(
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
