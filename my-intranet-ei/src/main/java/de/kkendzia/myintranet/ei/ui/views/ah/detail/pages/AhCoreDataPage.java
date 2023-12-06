package de.kkendzia.myintranet.ei.ui.views.ah.detail.pages;

import de.kkendzia.myintranet.ei._framework.view.page.AbstractLazyPage;
import de.kkendzia.myintranet.ei.ui.layouts.SectionLayout;
import de.kkendzia.myintranet.ei.ui.tools.binder.BufferedBinderList;
import de.kkendzia.myintranet.ei.ui.views.ah._shared.components.forms.AhAdressDataForm;
import de.kkendzia.myintranet.ei.ui.views.ah._shared.components.forms.AhCoreDataForm;
import de.kkendzia.myintranet.ei.ui.views.ah._shared.components.forms.AhMemberDataForm;
import de.kkendzia.myintranet.ei.ui.views.ah.detail.AhDetailPresenter;
import de.kkendzia.myintranet.ei.ui.views.ah.detail.model.AhDetailModel;

import static de.kkendzia.myintranet.ei.core.i18n.TranslationKeys.*;
import static java.util.Objects.requireNonNull;

public class AhCoreDataPage extends AbstractLazyPage<SectionLayout> implements AhDetailPage
{
    private final BufferedBinderList<AhDetailModel> binderList = new BufferedBinderList<>();
    private final AhDetailPresenter presenter;

    public AhCoreDataPage(AhDetailPresenter presenter)
    {
        this.presenter = requireNonNull(presenter, "presenter can't be null!");

        final var root = getContent();
        AhCoreDataForm frmCoreData = new AhCoreDataForm(binderList.createBinder(AhDetailModel::coreData));
        root.addSection(getTranslation(COMMON), frmCoreData);
        AhAdressDataForm frmAdressData = new AhAdressDataForm(binderList.createBinder(AhDetailModel::adressData));
        root.addSection(getTranslation(AdressKeys.ADRESS), frmAdressData);
        AhMemberDataForm frmMemberData = new AhMemberDataForm(binderList.createBinder(AhDetailModel::membershipData));
        root.addSection(getTranslation(MEMBERSHIP), frmMemberData);
    }

    @Override
    protected void onLoad()
    {
        AhDetailModel model = presenter.getState();
        binderList.readBeanAndCache(model);
    }
}
