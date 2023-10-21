package de.kkendzia.myintranet.ei.ui.views.other.mandant.detail;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.Route;
import de.kkendzia.myintranet.ei.core.i18n.TranslationKeys;
import de.kkendzia.myintranet.ei.core.parameters.HasViewParameter;
import de.kkendzia.myintranet.ei.core.view.AbstractEIView;
import de.kkendzia.myintranet.ei.core.view.layouts.TabsLayout;
import de.kkendzia.myintranet.ei.core.view.toolbar.ToolbarConfig;
import de.kkendzia.myintranet.ei.ui.components.tabs.PagedTabs;
import de.kkendzia.myintranet.ei.ui.layout.EIMainLayout;
import de.kkendzia.myintranet.ei.ui.views.other.mandant.detail.pages.MandantDetailPage;
import de.kkendzia.myintranet.ei.ui.views.other.mandant.detail.pages.MandantDetailsPage;
import de.kkendzia.myintranet.ei.ui.views.other.mandant.detail.pages.MandantSettingsPage;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "mandant", layout = EIMainLayout.class)
public class MandantDetailView extends AbstractEIView<TabsLayout<MandantDetailPage>> implements HasViewParameter<Long>
{
    private final MandantDetailPresenter presenter;

    @Autowired
    public MandantDetailView(MandantDetailPresenter presenter)
    {
        this.presenter = presenter;

        setPageTitle(getTranslation(TranslationKeys.MANDANTEN));
        setToolbarConfig(
                () -> new ToolbarConfig.Builder()
                        .title(getTranslation("mandant"))
                        .action(getTranslation("TEST"), () -> Notification.show("TEST!"))
                        .config(getContent().getTabs().getSelectedPage().getToolbarConfig())
                        .build());

        TabsLayout<MandantDetailPage> root = getContent();
        root.addSelectedPageChangeListener(e -> fireToolbarChange(e.isFromClient()));
        // 20.10.2023 KK TODO: I18N
        root.add(new PagedTabs.PagedTab<>(getTranslation("main"), new MandantDetailsPage(presenter)));
        root.add(new PagedTabs.PagedTab<>(getTranslation("settings"), new MandantSettingsPage(presenter)));
    }


    @Override
    public void beforeEnterView(BeforeEnterEvent event)
    {
        long id = getViewParameter();
        presenter.loadMandantById(id);
        getContent().refreshPages();
    }
}
