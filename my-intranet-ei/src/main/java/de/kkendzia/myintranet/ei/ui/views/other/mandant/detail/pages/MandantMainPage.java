package de.kkendzia.myintranet.ei.ui.views.other.mandant.detail.pages;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import de.kkendzia.myintranet.domain.shared.mandant.Mandant;
import de.kkendzia.myintranet.ei.core.i18n.TranslationKeys;
import de.kkendzia.myintranet.ei.core.view.page.AbstractLazyPage;
import de.kkendzia.myintranet.ei.core.view.toolbar.ToolbarConfig;
import de.kkendzia.myintranet.ei.ui.components.upload.ImageUpload;
import de.kkendzia.myintranet.ei.ui.views.other.mandant.components.MandantForm;
import de.kkendzia.myintranet.ei.ui.views.other.mandant.detail.MandantDetailPresenter;

import static java.util.Objects.requireNonNull;

public class MandantMainPage extends AbstractLazyPage<VerticalLayout> implements MandantDetailPage
{
    private final MandantForm frmMandant = new MandantForm();
    private final ImageUpload imgUpload = new ImageUpload();

    private final MandantDetailPresenter presenter;

    public MandantMainPage(MandantDetailPresenter presenter)
    {
        this.presenter = requireNonNull(presenter, "presenter can't be null!");

        setToolbarConfig(
                new ToolbarConfig.Builder()
                        .action(getTranslation(TranslationKeys.SAVE), this::save)
                        .build());

        VerticalLayout root = getContent();
        root.setPadding(false);
        root.setAlignItems(FlexComponent.Alignment.STRETCH);
        H2 hTitle = new H2(getTranslation("details"));
        root.add(hTitle);
        root.add(frmMandant);
        root.add(imgUpload);
    }

    private void save()
    {
        // TODO
        Mandant mandant = frmMandant.getBean();
        mandant.setImage(imgUpload.getValue());
        presenter.updateMandant();
    }

    protected void onLoad()
    {
        Mandant mandant = presenter.getMandant();
        frmMandant.setBean(mandant);
        imgUpload.setValue(mandant.getImage());
    }
}
