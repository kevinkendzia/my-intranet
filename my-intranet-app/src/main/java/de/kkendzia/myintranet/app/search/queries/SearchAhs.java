package de.kkendzia.myintranet.app.search.queries;

import de.kkendzia.myintranet.app._framework.cqrs.QueryHandler;
import de.kkendzia.myintranet.app._framework.cqrs.QueryHandler.Query;

import java.time.LocalDate;

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

    public record ResultItem(
            long id,
            int ahnr,
            String matchcode,
            LocalDate enterDate,
            LocalDate exitDate)
    {
        // just a record
    }

    public enum Failure
    {
        UNKNOWN
    }
}
