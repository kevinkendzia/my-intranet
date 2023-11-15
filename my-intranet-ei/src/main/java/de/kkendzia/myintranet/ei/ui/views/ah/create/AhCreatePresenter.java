package de.kkendzia.myintranet.ei.ui.views.ah.create;

import de.kkendzia.myintranet.domain.ah.Ah;
import de.kkendzia.myintranet.domain.ah.AhDAO;
import de.kkendzia.myintranet.domain.shared.adress.Adress;
import de.kkendzia.myintranet.domain.shared.adress.AdressDAO;
import de.kkendzia.myintranet.domain.shared.mandant.Mandant;
import de.kkendzia.myintranet.domain.shared.mandant.MandantDAO;
import de.kkendzia.myintranet.ei.core.presenter.Presenter;
import de.kkendzia.myintranet.ei.ui.views.ah._shared.model.AhAdressData;
import de.kkendzia.myintranet.ei.ui.views.ah._shared.model.AhCoreData;
import de.kkendzia.myintranet.ei.ui.views.ah._shared.model.AhMemberData;
import de.kkendzia.myintranet.ei.ui.views.ah.create.model.AhCreateRequest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Comparator;
import java.util.List;

import static java.util.Objects.requireNonNull;

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
    public long create(AhCreateRequest request)
    {
        requireNonNull(request, "request can't be null!");
        AhCoreData coreData = requireNonNull(request.coreData(), "request.coreData() can't be null!");
        AhAdressData adressData = requireNonNull(
                request.adressData(),
                "request.adressData() can't be null!");
        AhMemberData memberData = requireNonNull(
                request.memberData(),
                "request.memberData() can't be null!");

        // TODO Adresssuche?
        Adress adress = new Adress(
                0,
                adressData.getLine1(),
                adressData.getLine2(),
                adressData.getStreet(),
                adressData.getZip(),
                adressData.getCity(),
                adressData.getCountry());

        if (!adressDAO.exists(adress))
        {
            adress = adressDAO.create(adress);
        }

        Ah ah = ahDAO.create(new Ah(
                0,
                coreData.getAhnr(),
                coreData.getMatchcode(),
                coreData.getMandant(),
                coreData.getEnterDate(),
                null,
                adress,
                memberData.getRegulator(),
                memberData.getAssociation(),
                memberData.getMembershipForm()));

        return ah.getId();
    }

    public List<Mandant> loadMandantItems()
    {
        return mandantDAO.findAll().sorted(Comparator.comparing(Mandant::getName)).toList();
    }
}
