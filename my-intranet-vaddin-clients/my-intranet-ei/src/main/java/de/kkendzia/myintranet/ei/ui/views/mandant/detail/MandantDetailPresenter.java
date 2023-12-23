package de.kkendzia.myintranet.ei.ui.views.mandant.detail;

import de.kkendzia.myintranet.app._framework.cqrs.command.CommandMediator;
import de.kkendzia.myintranet.app._framework.cqrs.query.QueryMediator;
import de.kkendzia.myintranet.app.mandant._shared.MandantSheet;
import de.kkendzia.myintranet.app.mandant.commands.CreateMandant;
import de.kkendzia.myintranet.app.mandant.commands.UpdateMandant;
import de.kkendzia.myintranet.app.mandant.queries.GetMandantSheetByID;
import de.kkendzia.myintranet.domain.mandant.Mandant.MandantID;
import de.kkendzia.myintranet.ei._framework.presenter.EIPresenter;
import de.kkendzia.myintranet.ei._framework.presenter.Presenter;

import static de.kkendzia.myintranet.ei.ui.views.mandant.detail.MandantDetailPresenter.EditMode.CREATE;
import static de.kkendzia.myintranet.ei.ui.views.mandant.detail.MandantDetailPresenter.EditMode.EDIT;

@Presenter
public class MandantDetailPresenter implements EIPresenter
{
    private final transient QueryMediator quMediator;
    private final transient CommandMediator cmdMediator;

    // STATE
    private MandantSheet sheet;
    private EditMode editMode = CREATE;

    public MandantDetailPresenter(final QueryMediator quMediator, final CommandMediator cmdMediator)
    {
        this.quMediator = quMediator;
        this.cmdMediator = cmdMediator;
    }

    public void init(String id)
    {
        if (id != null)
        {
            this.sheet = quMediator.fetchOne(new GetMandantSheetByID(new MandantID(id))).getData();
            editMode = EDIT;
        }
        else
        {
            this.sheet = new MandantSheet(new MandantID(), "key", "new mandant", null);
            editMode = CREATE;
        }
    }

    public void save()
    {
        final var id =
                editMode == EDIT
                ? cmdMediator.call(new UpdateMandant(sheet)).getData()
                : cmdMediator.call(new CreateMandant(sheet)).getData();

        this.sheet = quMediator.fetchOne(new GetMandantSheetByID(id)).getData();
        editMode = EDIT;
    }

    //region GETTER
    public MandantSheet getSheet()
    {
        return this.sheet;
    }

    public EditMode getMode()
    {
        return this.editMode;
    }
    //endregion

    //region TYPES
    public enum EditMode
    {
        CREATE,
        EDIT
    }
    //endregion
}
