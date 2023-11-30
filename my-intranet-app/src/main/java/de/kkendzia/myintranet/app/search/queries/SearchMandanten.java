package de.kkendzia.myintranet.app.search.queries;

import de.kkendzia.myintranet.app._framework.cqrs.query.paged.PagedQuery;
import de.kkendzia.myintranet.app._shared.failures.SearchFailure;
import de.kkendzia.myintranet.domain.mandant.Mandant.MandantID;

public record SearchMandanten(String searchtext)
        implements PagedQuery<SearchMandanten.ResultItem, SearchFailure>
{
    public interface SearchMandantenHandler
            extends PagedQueryHandler<SearchMandanten, SearchMandanten.ResultItem, SearchFailure>
    {
        @Override
        default Class<SearchMandanten> getQueryClass()
        {
            return SearchMandanten.class;
        }
    }

    public record ResultItem(
            MandantID id,
            String name)
    {
        public String idString()
        {
            return id().toString();
        }
    }
}
