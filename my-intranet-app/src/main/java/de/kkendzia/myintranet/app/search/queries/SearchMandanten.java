package de.kkendzia.myintranet.app.search.queries;

import de.kkendzia.myintranet.app._framework.cqrs.QueryHandler;
import de.kkendzia.myintranet.app._framework.cqrs.QueryHandler.Query;

public record SearchMandanten(String searchtext) implements Query<SearchMandanten.ResultItem, SearchMandanten.Failure>
{
    interface SearchMandantenHandler extends QueryHandler<SearchMandanten, SearchMandanten.ResultItem, SearchMandanten.Failure>
    {
        @Override
        default Class<SearchMandanten> getQueryClass()
        {
            return SearchMandanten.class;
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
