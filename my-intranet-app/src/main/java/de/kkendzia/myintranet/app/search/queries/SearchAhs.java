package de.kkendzia.myintranet.app.search.queries;

import de.kkendzia.myintranet.app._framework.cqrs.query.paged.PagedQuery;
import de.kkendzia.myintranet.app._shared.failures.SearchFailure;
import de.kkendzia.myintranet.domain.ah.Ah.AhID;
import de.kkendzia.myintranet.domain.ah.Ah.Ahnr;

import java.time.LocalDate;

public record SearchAhs(String searchtext) implements PagedQuery<SearchAhs.ResultItem, SearchFailure>
{
    public interface SearchAhsHandler extends PagedQueryHandler<SearchAhs, SearchAhs.ResultItem, SearchFailure>
    {
        @Override
        default Class<SearchAhs> getQueryClass()
        {
            return SearchAhs.class;
        }
    }

    public record ResultItem(
            AhID id,
            Ahnr ahnr,
            String matchcode,
            LocalDate enterDate,
            LocalDate exitDate)
    {
        public String idString()
        {
            return id().toString();
        }
    }
}
