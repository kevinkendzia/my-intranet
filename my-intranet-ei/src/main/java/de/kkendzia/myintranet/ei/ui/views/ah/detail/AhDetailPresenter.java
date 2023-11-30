package de.kkendzia.myintranet.ei.ui.views.ah.detail;

import de.kkendzia.myintranet.app._framework.cqrs.query.QueryMediator;
import de.kkendzia.myintranet.app.ah._shared.AhSheet;
import de.kkendzia.myintranet.app.ah.queries.FindAhByID;
import de.kkendzia.myintranet.domain.ah.Ah.AhID;
import de.kkendzia.myintranet.ei._framework.presenter.AbstractStatefullPresenter;
import de.kkendzia.myintranet.ei._framework.presenter.Presenter;
import de.kkendzia.myintranet.ei.ui.views.ah.detail.model.AhDetailModel;

import static java.util.Objects.requireNonNull;

@Presenter
public class AhDetailPresenter extends AbstractStatefullPresenter<AhDetailModel>
{
    private final transient QueryMediator quMediator;

    public AhDetailPresenter(final QueryMediator quMediator)
    {
        this.quMediator = requireNonNull(quMediator, "quMediator can't be null!");
    }

    public void loadAhById(AhID id)
    {
        final var ah = quMediator.fetchOne(new FindAhByID(id)).getData();
        setState(
                new AhDetailModel(
                        new AhSheet.CoreSection(
                                ah.ahnr(),
                                ah.matchcode(),
                                ah.mandant(),
                                ah.enterDate(),
                                ah.exitDate()),
                        new AhSheet.AdressSection(
                                ah.adress().line1(),
                                ah.adress().line2(),
                                ah.adress().street(),
                                ah.adress().zip(),
                                ah.adress().city(),
                                ah.adress().country()),
                        new AhSheet.MembershipSection(
                                ah.regulierer(),
                                ah.verband(),
                                ah.mitgliedsForm())));
    }

    public void save()
    {
        final var state = getState();
        requireNonNull(state, "state can't be null!");
        // 29.10.2023 KK TODO: Mapping
//        setState(ahDAO.update(state));
    }
}
