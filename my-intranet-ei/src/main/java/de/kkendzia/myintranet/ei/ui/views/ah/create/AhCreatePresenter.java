package de.kkendzia.myintranet.ei.ui.views.ah.create;

import de.kkendzia.myintranet.app._framework.cqrs.CommandMediator;
import de.kkendzia.myintranet.app.ah.commands.CreateAh;
import de.kkendzia.myintranet.domain.ah.Ah;
import de.kkendzia.myintranet.domain.ah.AhAdress;
import de.kkendzia.myintranet.domain.ah.AhRepository;
import de.kkendzia.myintranet.domain.mandant.Mandant;
import de.kkendzia.myintranet.domain.mandant.MandantRepository;
import de.kkendzia.myintranet.ei.core.presenter.Presenter;
import de.kkendzia.myintranet.app.ah.commands.AhAdressData;
import de.kkendzia.myintranet.app.ah.commands.AhCoreData;
import de.kkendzia.myintranet.app.ah.commands.AhMemberData;
import de.kkendzia.myintranet.ei.ui.views.ah.create.model.AhCreateRequest;

import java.util.Comparator;
import java.util.List;

import static java.util.Objects.requireNonNull;

@Presenter
public class AhCreatePresenter
{
    private AhRepository ahDAO;
    private MandantRepository mandantDAO;

    private CommandMediator cmdMediator;

    // TODO Transactions?
    // TODO MapStruct?
    public long create(AhCreateRequest request)
    {
        cmdMediator.execute(new CreateAh(request.coreData(),request.adressData(),request.memberData()));


        requireNonNull(request, "request can't be null!");
        AhCoreData coreData = requireNonNull(request.coreData(), "request.coreData() can't be null!");
        AhAdressData adressData = requireNonNull(
                request.adressData(),
                "request.adressData() can't be null!");
        AhMemberData memberData = requireNonNull(
                request.memberData(),
                "request.memberData() can't be null!");

        // TODO Adresssuche?
        AhAdress adress = new AhAdress(
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
