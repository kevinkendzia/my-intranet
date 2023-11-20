package de.kkendzia.myintranet.ei.ui.views.ah.detail;

import de.kkendzia.myintranet.domain.ah.AhRepository;
import de.kkendzia.myintranet.ei.core.presenter.AbstractStatefullPresenter;
import de.kkendzia.myintranet.ei.core.presenter.Presenter;
import de.kkendzia.myintranet.ei.ui.errors.UnknownIDError.UnknownIDException;
import de.kkendzia.myintranet.ei.ui.views.ah._shared.model.AhAdressData;
import de.kkendzia.myintranet.ei.ui.views.ah._shared.model.AhCoreData;
import de.kkendzia.myintranet.ei.ui.views.ah._shared.model.AhMemberData;
import de.kkendzia.myintranet.ei.ui.views.ah.detail.model.AhDetailModel;

import static java.util.Objects.requireNonNull;

@Presenter
public class AhDetailPresenter extends AbstractStatefullPresenter<AhDetailModel>
{
    private final AhRepository ahDAO;

    public AhDetailPresenter(final AhRepository ahDAO)
    {
        this.ahDAO = requireNonNull(ahDAO, "ahDAO can't be null!");
    }

    public void loadAhById(long id)
    {
        final var ah = ahDAO.findOptionalById(id).orElseThrow(UnknownIDException::new);
        setState(
                new AhDetailModel(
                        new AhCoreData(
                                ah.getAhnr(),
                                ah.getMatchcode(),
                                ah.getMandant(),
                                ah.getEnterDate(),
                                ah.getExitDate()),
                        new AhAdressData(
                                ah.getAdress().getLine1(),
                                ah.getAdress().getLine2(),
                                ah.getAdress().getStreet(),
                                ah.getAdress().getZip(),
                                ah.getAdress().getCity(),
                                ah.getAdress().getCountry()),
                        new AhMemberData(
                                ah.getRegulator(),
                                ah.getVerband(),
                                ah.getMembershipForm())));
    }

    public void save()
    {
        final var state = getState();
        requireNonNull(state, "state can't be null!");
        // 29.10.2023 KK TODO: Mapping
//        setState(ahDAO.update(state));
    }
}
