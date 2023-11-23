package de.kkendzia.myintranet.app.search.queries;

import de.kkendzia.myintranet.app._framework.cqrs.QueryHandler;
import de.kkendzia.myintranet.app._framework.cqrs.QueryHandler.Query;

public record SearchEIUsers(String searchtext) implements Query<SearchEIUsers.ResultItem, SearchEIUsers.Failure>
{
    interface SearchEIUserHandler extends QueryHandler<SearchEIUsers, SearchEIUsers.ResultItem, SearchEIUsers.Failure>
    {
        @Override
        default Class<SearchEIUsers> getQueryClass()
        {
            return SearchEIUsers.class;
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
