package de.kkendzia.myintranet.ei.ui.views.ah.create;

import de.kkendzia.myintranet.domain.ah.Ah;
import de.kkendzia.myintranet.domain.ah.AhDAO;
import de.kkendzia.myintranet.domain.shared.Adress;
import de.kkendzia.myintranet.domain.shared.AdressDAO;
import de.kkendzia.myintranet.domain.shared.Mandant;
import de.kkendzia.myintranet.ei.core.annotations.Presenter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Random;

@Presenter
public class AhCreatePresenter
{
    @Autowired
    private AhDAO ahDAO;
    @Autowired
    private AdressDAO adressDAO;

    // TODO Transactions?
    // TODO MapStruct?
    public void save(AhCreateRequest request)
    {
        if (request.coreData() != null)
        {
            ahDAO.create(new Ah(
                    0,
                    request.coreData().getAhnr(),
                    request.coreData().getMatchcode(),
                    request.coreData().getMandant()));
        }
        if(request.adressData() != null)
        {
            // TODO Adresssuche?
            adressDAO.create(request.adressData());
        }
    }

    public record AhCreateRequest(
            AhData coreData,
            Adress adressData)
    {
        // just a record
    }

    public static class AhData
    {
        private final long id;
        private Ah.Ahnr ahnr;
        private String matchcode;
        private Mandant mandant;

        public AhData()
        {
            this(new Random().nextInt(1000), new Ah.Ahnr(12345), "TEST AH", new Mandant(new Random().nextInt(1000), "TEST MANDANT"));
        }

        public AhData(
                long id,
                Ah.Ahnr ahnr,
                String matchcode,
                Mandant mandant)
        {
            this.id=id;
            this.ahnr = ahnr;
            this.matchcode = matchcode;
            this.mandant = mandant;
        }

        public long getId()
        {
            return id;
        }

        public Ah.Ahnr getAhnr()
        {
            return ahnr;
        }

        public void setAhnr(Ah.Ahnr ahnr)
        {
            this.ahnr = ahnr;
        }

        public String getMatchcode()
        {
            return matchcode;
        }

        public void setMatchcode(String matchcode)
        {
            this.matchcode = matchcode;
        }

        public Mandant getMandant()
        {
            return mandant;
        }

        public void setMandant(Mandant mandant)
        {
            this.mandant = mandant;
        }
    }
}
