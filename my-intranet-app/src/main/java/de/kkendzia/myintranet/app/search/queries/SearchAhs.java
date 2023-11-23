package de.kkendzia.myintranet.app.search.queries;

import de.kkendzia.myintranet.app._framework.cqrs.query.QueryHandler;
import de.kkendzia.myintranet.app._framework.cqrs.query.paged.PagedQuery;
import de.kkendzia.myintranet.domain.ah.Ah.AhID;

import java.time.LocalDate;

public record SearchAhs(String searchtext) implements PagedQuery<SearchAhs.ResultItem, SearchAhs.Failure>
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
            AhID id,
            int ahnr,
            String matchcode,
            LocalDate enterDate,
            LocalDate exitDate)
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
