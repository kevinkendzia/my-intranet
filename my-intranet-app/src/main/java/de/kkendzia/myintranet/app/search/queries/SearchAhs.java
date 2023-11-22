package de.kkendzia.myintranet.app.search.queries;

import de.kkendzia.myintranet.app._framework.cqrs.QueryHandler;
import de.kkendzia.myintranet.app._framework.cqrs.QueryHandler.Query;

public record SearchAhs(String searchtext) implements Query<SearchAhs.ResultItem, SearchAhs.Failure>
{
    interface SearchAhsHandler extends QueryHandler<SearchAhs, SearchAhs.ResultItem, SearchAhs.Failure>
    {
        @Override
        default Class<SearchAhs> getQueryClass()
        {
            return SearchAhs.class;
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
