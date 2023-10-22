package de.kkendzia.myintranet.ei.ui.views.mandant.detail.pages;

import com.vaadin.flow.data.binder.Binder;
import de.kkendzia.myintranet.domain.shared.mandant.Mandant;
import de.kkendzia.myintranet.ei.core.view.layouts.SectionLayout;
import de.kkendzia.myintranet.ei.core.view.page.AbstractPage;
import de.kkendzia.myintranet.ei.core.view.page.SaveablePage;
import de.kkendzia.myintranet.ei.ui.components.upload.ImageUpload;
import de.kkendzia.myintranet.ei.ui.views.mandant.components.MandantForm;
import de.kkendzia.myintranet.ei.ui.views.mandant.detail.MandantDetailPresenter;

import static de.kkendzia.myintranet.ei.core.i18n.TranslationKeys.DETAILS;
import static de.kkendzia.myintranet.ei.core.i18n.TranslationKeys.IMAGE;
import static java.util.Objects.requireNonNull;

public class MandantDetailsPage extends AbstractPage<SectionLayout> implements MandantDetailPage, SaveablePage
{
    private Binder<Mandant> binder = new Binder<>();
    private final MandantForm frmMandant = new MandantForm(binder);
    private final ImageUpload imgUpload = new ImageUpload();

    private final MandantDetailPresenter presenter;

    public MandantDetailsPage(MandantDetailPresenter presenter)
    {
        this.presenter = requireNonNull(presenter, "presenter can't be null!");

        SectionLayout root = getContent();
        root.add(getTranslation(DETAILS), frmMandant);
        root.add(getTranslation(IMAGE), imgUpload);

        binder
                .forField(imgUpload)
                .bind(Mandant::getImage, Mandant::setImage);
    }

    @Override
    public boolean hasChanges()
    {
        return frmMandant.hasChanges();
    }

    public boolean validate()
    {
        return frmMandant.validate();
    }

    public void refresh()
    {
        Mandant mandant = presenter.getMandant();
        frmMandant.setBean(mandant);
        imgUpload.setValue(mandant.getImage());
    }
}
