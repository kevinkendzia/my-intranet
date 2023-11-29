package de.kkendzia.myintranet.app.mandant.queries;

import de.kkendzia.myintranet.app._framework.cqrs.query.paged.PagedQuery;
import de.kkendzia.myintranet.domain.mandant.Mandant.MandantID;

public record ListMandanten()
        implements PagedQuery<ListMandanten.MandantItem, ListMandanten.Failure>
{
    public interface ListMandantenHandler
            extends QueryHandler<ListMandanten, MandantItem, ListMandanten.Failure>
    {
        @Override
        default Class<ListMandanten> getQueryClass()
        {
            return ListMandanten.class;
        }
    }

    public record MandantItem(
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
