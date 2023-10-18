package de.kkendzia.myintranet.ei.ui.views.other.mandant.detail;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.Route;
import de.kkendzia.myintranet.domain.shared.Mandant;
import de.kkendzia.myintranet.ei.core.i18n.TranslationKeys;
import de.kkendzia.myintranet.ei.core.parameters.HasViewParameter;
import de.kkendzia.myintranet.ei.core.view.AbstractEIView;
import de.kkendzia.myintranet.ei.core.view.toolbar.ToolbarConfig;
import de.kkendzia.myintranet.ei.ui.components.upload.ImageUpload;
import de.kkendzia.myintranet.ei.ui.layout.EIMainLayout;
import de.kkendzia.myintranet.ei.ui.views.other.mandant.components.MandantForm;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "mandant", layout = EIMainLayout.class)
public class MandantDetailView extends AbstractEIView<VerticalLayout> implements HasViewParameter<Long>
{
    private final H2 hTitle = new H2(getTranslation("mandant"));
    private final MandantForm frmMandant = new MandantForm();
    private final ImageUpload imgUpload = new ImageUpload();

    private final MandantDetailPresenter presenter;

    @Autowired
    public MandantDetailView(MandantDetailPresenter presenter)
    {
        this.presenter = presenter;

        setToolbarConfig(
                new ToolbarConfig.Builder()
                        .title(getTranslation("mandant"))
                        .action(getTranslation(TranslationKeys.SAVE), this::save)
                        .build());

        VerticalLayout root = getContent();
        root.setPadding(false);
        root.setAlignItems(FlexComponent.Alignment.STRETCH);
        root.add(hTitle);
        root.add(frmMandant);
        root.add(imgUpload);
    }

    private void save()
    {
        // TODO
        Mandant mandant = frmMandant.getBean();
        mandant.setImage(imgUpload.getValue());
        presenter.save(mandant);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event)
    {
        super.beforeEnter(event);
        long id = getViewParameter();
        Mandant mandant = presenter.loadMandantById(id);
        frmMandant.setBean(mandant);
        imgUpload.setValue(mandant.getImage());
    }
}
