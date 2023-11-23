package de.kkendzia.myintranet.app.mandant.queries;

import de.kkendzia.myintranet.app._framework.cqrs.query.QueryHandler;
import de.kkendzia.myintranet.app._shared.FindByIDFailure;
import de.kkendzia.myintranet.domain.mandant.Mandant;

public record FindMandantByID(Mandant.MandantID id) implements QueryHandler.Query<Mandant, FindByIDFailure>
{
    public interface FindMandantByIDHandler extends QueryHandler<FindMandantByID, Mandant, FindByIDFailure>
    {
        @Override
        default Class<FindMandantByID> getQueryClass()
        {
            return FindMandantByID.class;
        }
    }
}
