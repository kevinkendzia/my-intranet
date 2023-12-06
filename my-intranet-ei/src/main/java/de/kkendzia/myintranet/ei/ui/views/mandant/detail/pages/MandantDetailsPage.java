package de.kkendzia.myintranet.ei.ui.views.mandant.detail.pages;

import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.server.StreamResource;
import de.kkendzia.myintranet.app._framework.result.VoidResult;
import de.kkendzia.myintranet.app.mandant._shared.MandantSheet;
import de.kkendzia.myintranet.app.mandant._shared.MandantSheet.File;
import de.kkendzia.myintranet.ei._framework.view.page.AbstractPage;
import de.kkendzia.myintranet.ei._framework.view.page.SaveablePage;
import de.kkendzia.myintranet.ei.ui.components.files.images.ImagePreview;
import de.kkendzia.myintranet.ei.ui.layouts.SectionLayout;
import de.kkendzia.myintranet.ei.ui.tools.binder.BufferedBinderList;
import de.kkendzia.myintranet.ei.ui.views.mandant.components.MandantForm;
import de.kkendzia.myintranet.ei.ui.views.mandant.detail.MandantDetailPresenter;

import java.io.ByteArrayInputStream;
import java.util.List;

import static com.vaadin.flow.component.orderedlayout.FlexLayout.FlexWrap.WRAP;
import static de.kkendzia.myintranet.ei.core.i18n.TranslationKeys.DETAILS;
import static java.util.Objects.requireNonNull;

public class MandantDetailsPage extends AbstractPage<FlexLayout>
        implements MandantDetailPage, SaveablePage
{
    private final BufferedBinderList<MandantSheet> binder = new BufferedBinderList<>();
    private final MandantDetailPresenter presenter;

    public MandantDetailsPage(MandantDetailPresenter presenter)
    {
        this.presenter = requireNonNull(presenter, "presenter can't be null!");

        ImagePreview<File> imagePreview =
                new ImagePreview<>(item ->
                        new StreamResource(item.name(), () -> new ByteArrayInputStream(item.data())));
        final var ipStyle = imagePreview.getStyle();
        ipStyle.set("flex", "1 1 22em");
        imagePreview.setMaxWidth("44em");

        MandantForm frmMandant = new MandantForm(binder.createBinder());

        final var sectionLayout = new SectionLayout();
        sectionLayout.getStyle().set("flex", "1 1 22em");
        sectionLayout.setMaxWidth("44em");
        sectionLayout.addSection(getTranslation(DETAILS), frmMandant);

        FlexLayout root = getContent();
        root.setFlexWrap(WRAP);
        root.add(imagePreview);
        root.add(sectionLayout);

        binder
                .createBinder()
                .forField(imagePreview)
                .bind(MandantSheet::getFiles, MandantSheet::setFiles);
    }

    @Override
    public boolean hasChanges()
    {
        return binder.hasChanges();
    }

    @Override
    public VoidResult<List<String>> validate()
    {
        return binder.validateAll();
    }

    @Override
    public void onSave()
    {
        try
        {
            binder.writeBeanToCache();
        }
        catch (ValidationException e)
        {
            throw new IllegalStateException("Couldn't write bean!", e);
        }
    }

    @Override
    public void refresh()
    {
        MandantSheet mandant = presenter.getSheet();
        binder.readBeanAndCache(mandant);
    }
}
