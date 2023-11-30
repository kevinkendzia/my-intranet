package de.kkendzia.myintranet.ei.ui.views.ah.detail.pages;

import de.kkendzia.myintranet.ei._framework.view.page.AbstractLazyPage;
import de.kkendzia.myintranet.ei.ui.components.form.FormBinder;
import de.kkendzia.myintranet.ei.ui.layouts.SectionLayout;
import de.kkendzia.myintranet.ei.ui.views.ah._shared.components.forms.AhAdressDataForm;
import de.kkendzia.myintranet.ei.ui.views.ah._shared.components.forms.AhCoreDataForm;
import de.kkendzia.myintranet.ei.ui.views.ah._shared.components.forms.AhMemberDataForm;
import de.kkendzia.myintranet.ei.ui.views.ah.detail.AhDetailPresenter;
import de.kkendzia.myintranet.ei.ui.views.ah.detail.model.AhDetailModel;

import static de.kkendzia.myintranet.ei.core.i18n.TranslationKeys.*;
import static java.util.Objects.requireNonNull;

public class AhCoreDataPage extends AbstractLazyPage<SectionLayout> implements AhDetailPage
{
    private final AhCoreDataForm frmCoreData = new AhCoreDataForm();
    private final AhAdressDataForm frmAdressData = new AhAdressDataForm();
    private final AhMemberDataForm frmMemberData = new AhMemberDataForm();
    private final FormBinder<AhDetailModel> formBinder = new FormBinder<>();

    private final AhDetailPresenter presenter;

    public AhCoreDataPage(AhDetailPresenter presenter)
    {
        this.presenter = requireNonNull(presenter, "presenter can't be null!");

        final var root = getContent();
        root.addSection(getTranslation(COMMON), frmCoreData);
        root.addSection(getTranslation(ADRESS), frmAdressData);
        root.addSection(getTranslation(MEMBERSHIP), frmMemberData);

        formBinder.bind(frmCoreData, AhDetailModel::coreData);
        formBinder.bind(frmAdressData, AhDetailModel::adressData);
        formBinder.bind(frmMemberData, AhDetailModel::memberData);
    }

    @Override
    protected void onLoad()
    {
        AhDetailModel model = presenter.getState();
        formBinder.setBean(model);
    }
}
