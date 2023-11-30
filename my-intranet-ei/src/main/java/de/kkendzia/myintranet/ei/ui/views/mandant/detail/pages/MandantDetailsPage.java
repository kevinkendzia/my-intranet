package de.kkendzia.myintranet.ei.ui.views.mandant.detail.pages;

import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.BinderValidationStatus;
import de.kkendzia.myintranet.app.mandant._shared.MandantSheet;
import de.kkendzia.myintranet.ei._framework.view.page.AbstractPage;
import de.kkendzia.myintranet.ei._framework.view.page.SaveablePage;
import de.kkendzia.myintranet.ei.ui.components.upload.ImageUpload;
import de.kkendzia.myintranet.ei.ui.layouts.SectionLayout;
import de.kkendzia.myintranet.ei.ui.views.mandant.components.MandantForm;
import de.kkendzia.myintranet.ei.ui.views.mandant.detail.MandantDetailPresenter;

import static de.kkendzia.myintranet.ei.core.i18n.TranslationKeys.DETAILS;
import static de.kkendzia.myintranet.ei.core.i18n.TranslationKeys.IMAGE;
import static java.util.Objects.requireNonNull;

public class MandantDetailsPage extends AbstractPage<SectionLayout>
        implements MandantDetailPage, SaveablePage<MandantSheet>
{
    private final Binder<MandantSheet> binder = new Binder<>();
    private final MandantForm frmMandant = new MandantForm(binder);
    private final ImageUpload imgUpload = new ImageUpload();

    private final MandantDetailPresenter presenter;

    public MandantDetailsPage(MandantDetailPresenter presenter)
    {
        this.presenter = requireNonNull(presenter, "presenter can't be null!");

        SectionLayout root = getContent();
        root.addSection(getTranslation(DETAILS), frmMandant);
        root.addSection(getTranslation(IMAGE), imgUpload);

        binder
                .forField(imgUpload)
                .bind(MandantSheet::getImage, MandantSheet::setImage);
    }

    @Override
    public boolean hasChanges()
    {
        return frmMandant.hasChanges();
    }

    @Override
    public BinderValidationStatus<MandantSheet> validate()
    {
        return frmMandant.validate();
    }

    @Override
    public void onSave()
    {
        frmMandant.getBean();
    }

    @Override
    public void refresh()
    {
        MandantSheet mandant = presenter.getSheet();
        frmMandant.setBean(mandant);
        imgUpload.setValue(mandant.getImage());
    }
}
