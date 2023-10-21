package de.kkendzia.myintranet.ei.ui.views.other.mandant.detail.pages;

import de.kkendzia.myintranet.domain.shared.mandant.Mandant;
import de.kkendzia.myintranet.ei.core.view.layouts.SectionLayout;
import de.kkendzia.myintranet.ei.core.view.page.AbstractPage;
import de.kkendzia.myintranet.ei.core.view.toolbar.ToolbarConfig;
import de.kkendzia.myintranet.ei.ui.components.upload.ImageUpload;
import de.kkendzia.myintranet.ei.ui.views.other.mandant.components.MandantForm;
import de.kkendzia.myintranet.ei.ui.views.other.mandant.detail.MandantDetailPresenter;

import static de.kkendzia.myintranet.ei.core.i18n.TranslationKeys.SAVE;
import static java.util.Objects.requireNonNull;

public class MandantDetailsPage extends AbstractPage<SectionLayout> implements MandantDetailPage
{
    private final MandantForm frmMandant = new MandantForm();
    private final ImageUpload imgUpload = new ImageUpload();

    private final MandantDetailPresenter presenter;

    public MandantDetailsPage(MandantDetailPresenter presenter)
    {
        this.presenter = requireNonNull(presenter, "presenter can't be null!");

        setToolbarConfig(
                new ToolbarConfig.Builder()
                        .action(getTranslation(SAVE), this::save)
                        .build());

        SectionLayout root = getContent();
        root.add(getTranslation("details"), frmMandant);
        root.add(getTranslation("image"), imgUpload);
    }

    private void save()
    {
        // TODO
        Mandant mandant = frmMandant.getBean();
        mandant.setImage(imgUpload.getValue());
        presenter.updateMandant();
    }

    public void refresh()
    {
        Mandant mandant = presenter.getMandant();
        frmMandant.setBean(mandant);
        imgUpload.setValue(mandant.getImage());
    }
}
