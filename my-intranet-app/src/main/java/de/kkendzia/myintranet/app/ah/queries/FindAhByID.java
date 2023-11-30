package de.kkendzia.myintranet.app.ah.queries;

import de.kkendzia.myintranet.app._framework.cqrs.query.SingleResultQuery;
import de.kkendzia.myintranet.app._shared.failures.FindByIDFailure;
import de.kkendzia.myintranet.app.mandant.queries.ListMandanten.MandantItem;
import de.kkendzia.myintranet.domain.ah.Ah;
import de.kkendzia.myintranet.domain.ah.Ah.AhID;
import de.kkendzia.myintranet.domain.ah.AhAdress;
import de.kkendzia.myintranet.domain.mitgliedsform.MitgliedsForm;
import de.kkendzia.myintranet.domain.regulierer.Regulierer;
import de.kkendzia.myintranet.domain.verband.Verband;

import java.time.LocalDate;

public record FindAhByID(AhID id) implements SingleResultQuery<FindAhByID.AhData, FindByIDFailure>
{
    public interface FindAhByIDHandler extends SingleResultQueryHandler<FindAhByID, AhData, FindByIDFailure>
    {
        @Override
        default Class<FindAhByID> getQueryClass()
        {
            return FindAhByID.class;
        }
    }

    public record AhData(
            Ah.Ahnr ahnr,
            String matchcode,
            LocalDate enterDate,
            LocalDate exitDate,
            AhAdress adress,
            MandantItem mandant,
            Regulierer regulierer,
            Verband verband,
            MitgliedsForm mitgliedsForm)
    {
        // just a record
    }
}
