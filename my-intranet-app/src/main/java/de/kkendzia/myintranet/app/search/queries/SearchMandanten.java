package de.kkendzia.myintranet.app.search.queries;

import de.kkendzia.myintranet.app._framework.cqrs.query.QueryHandler;
import de.kkendzia.myintranet.app._framework.cqrs.query.paged.PagedQuery;
import de.kkendzia.myintranet.domain.mandant.Mandant.MandantID;

public record SearchMandanten(String searchtext)
        implements PagedQuery<SearchMandanten.ResultItem, SearchMandanten.Failure>
{
    public interface SearchMandantenHandler
            extends QueryHandler<SearchMandanten, SearchMandanten.ResultItem, SearchMandanten.Failure>
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

    public enum Failure
    {
        UNKNOWN
    }
}
