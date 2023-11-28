package de.kkendzia.myintranet.app.mandant.queries;

import de.kkendzia.myintranet.app._framework.cqrs.query.QueryHandler;
import de.kkendzia.myintranet.app._shared.FindByIDFailure;
import de.kkendzia.myintranet.app.mandant._shared.MandantSheet;
import de.kkendzia.myintranet.domain.mandant.Mandant.MandantID;

public record GetMandantSheetByID(MandantID id)
        implements QueryHandler.Query<MandantSheet, FindByIDFailure>
{
    public interface GetEditSheetByIDHandler extends QueryHandler<GetMandantSheetByID, MandantSheet, FindByIDFailure>
    {
        @Override
        default Class<GetMandantSheetByID> getQueryClass()
        {
            return GetMandantSheetByID.class;
        }
    }

}
