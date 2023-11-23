package de.kkendzia.myintranet.ei.ui.views.ah.create;

import de.kkendzia.myintranet.app._framework.cqrs.command.CommandMediator;
import de.kkendzia.myintranet.app._framework.cqrs.query.QueryMediator;
import de.kkendzia.myintranet.app._framework.cqrs.query.paged.Order;
import de.kkendzia.myintranet.app.ah.commands.CreateAh;
import de.kkendzia.myintranet.app.mandant.queries.ListMandanten;
import de.kkendzia.myintranet.app.mandant.queries.ListMandanten.MandantItem;
import de.kkendzia.myintranet.domain.ah.Ah.AhID;
import de.kkendzia.myintranet.domain.mandant.Mandant;
import de.kkendzia.myintranet.ei.core.presenter.Presenter;
import de.kkendzia.myintranet.ei.ui.views.ah.create.model.AhCreateRequest;

import java.util.List;

import static de.kkendzia.myintranet.app._framework.cqrs.query.paged.Paging.allSorted;
import static java.util.Objects.requireNonNull;

@Presenter
public class AhCreatePresenter
{
    private final CommandMediator cmdMediator;
    private final QueryMediator quMediator;

    public AhCreatePresenter(final CommandMediator cmdMediator, final QueryMediator quMediator)
    {
        this.cmdMediator = requireNonNull(cmdMediator, "cmdMediator can't be null!");
        this.quMediator = requireNonNull(quMediator, "quMediator can't be null!");
    }

    public AhID create(AhCreateRequest request)
    {
        return cmdMediator.call(new CreateAh(request.coreData(), request.adressData(), request.memberData())).getData();
    }

    public List<MandantItem> loadMandantItems()
    {
        return quMediator
                .fetchAll(
                        new ListMandanten(),
                        allSorted(List.of(Order.asc(Mandant.PROPERTY_NAME))))
                .toList();
    }
}
