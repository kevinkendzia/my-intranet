package de.kkendzia.myintranet.app.search.queries;

import de.kkendzia.myintranet.app._framework.cqrs.QueryHandler;
import de.kkendzia.myintranet.app._framework.cqrs.QueryHandler.Query;

public record SearchEIUser(String searchtext) implements Query<SearchEIUser.ResultItem, SearchEIUser.Failure>
{
    interface SearchEIUserHandler extends QueryHandler<SearchEIUser, SearchEIUser.ResultItem, SearchEIUser.Failure>
    {
        @Override
        default Class<SearchEIUser> getQueryClass()
        {
            return SearchEIUser.class;
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
