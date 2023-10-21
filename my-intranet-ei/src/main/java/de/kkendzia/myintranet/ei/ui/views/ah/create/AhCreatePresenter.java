package de.kkendzia.myintranet.ei.ui.views.ah.create;

import de.kkendzia.myintranet.domain.ah.Ah;
import de.kkendzia.myintranet.domain.ah.AhDAO;
import de.kkendzia.myintranet.domain.shared.adress.Adress;
import de.kkendzia.myintranet.domain.shared.adress.AdressDAO;
import de.kkendzia.myintranet.domain.shared.mandant.Mandant;
import de.kkendzia.myintranet.domain.shared.mandant.MandantDAO;
import de.kkendzia.myintranet.ei.core.presenter.Presenter;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Presenter
public class AhCreatePresenter
{
    @Autowired
    private AhDAO ahDAO;
    @Autowired
    private AdressDAO adressDAO;
    @Autowired
    private MandantDAO mandantDAO;

    // TODO Transactions?
    // TODO MapStruct?
    public void save(AhCreateRequest request)
    {
        AhData coreData = request.coreData();
        if (coreData != null)
        {
            ahDAO.create(new Ah(
                    0,
                    coreData.getAhnr(),
                    coreData.getMatchcode(),
                    coreData.getMandant(),
                    // TODO
                    LocalDate.now(),
                    LocalDate.now()));
        }
        if(request.adressData() != null)
        {
            // TODO Adresssuche?
            adressDAO.create(request.adressData());
        }
    }

    public List<Mandant> loadMandantItems()
    {
        return mandantDAO.findAll().sorted(Comparator.comparing(Mandant::getName)).toList();
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
        private LocalDate enterDate;

        public AhData(
                long id,
                Ah.Ahnr ahnr,
                String matchcode,
                Mandant mandant,
                LocalDate enterDate)
        {
            this.id=id;
            this.ahnr = ahnr;
            this.matchcode = matchcode;
            this.mandant = mandant;
            this.enterDate=enterDate;
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

        public LocalDate getEnterDate()
        {
            return enterDate;
        }

        public void setEnterDate(LocalDate enterDate)
        {
            this.enterDate = enterDate;
        }
    }
}
