package de.kkendzia.myintranet.app.mandant.queries;

import de.kkendzia.myintranet.app._framework.cqrs.query.SingleResultQuery;
import de.kkendzia.myintranet.app._shared.failures.FindByIDFailure;
import de.kkendzia.myintranet.app.mandant._shared.MandantSheet;
import de.kkendzia.myintranet.domain.mandant.Mandant.MandantID;

public record GetMandantSheetByID(MandantID id)
        implements SingleResultQuery<MandantSheet, FindByIDFailure>
{
    public interface GetEditSheetByIDHandler
            extends SingleResultQueryHandler<GetMandantSheetByID, MandantSheet, FindByIDFailure>
    {
        @Override
        default Class<GetMandantSheetByID> getQueryClass()
        {
            return GetMandantSheetByID.class;
        }
    }

}
