package de.kkendzia.myintranet.ei.ui.views.other.mandant.detail;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.Route;
import de.kkendzia.myintranet.ei.core.parameters.HasViewParameter;
import de.kkendzia.myintranet.ei.core.view.AbstractEIView;
import de.kkendzia.myintranet.ei.core.view.toolbar.ToolbarConfig;
import de.kkendzia.myintranet.ei.ui.components.tabs.PagedTabs;
import de.kkendzia.myintranet.ei.ui.layout.EIMainLayout;
import de.kkendzia.myintranet.ei.ui.views.other.mandant.detail.pages.MandantDetailPage;
import de.kkendzia.myintranet.ei.ui.views.other.mandant.detail.pages.MandantMainPage;
import de.kkendzia.myintranet.ei.ui.views.other.mandant.detail.pages.MandantSettingsPage;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "mandant", layout = EIMainLayout.class)
public class MandantDetailView extends AbstractEIView<VerticalLayout> implements HasViewParameter<Long>
{
    private PagedTabs<MandantDetailPage> tabs = new PagedTabs<>();
    private final MandantDetailPresenter presenter;

    @Autowired
    public MandantDetailView(MandantDetailPresenter presenter)
    {
        this.presenter = presenter;

        VerticalLayout vlContent = new VerticalLayout();
        vlContent.setPadding(false);

        tabs.addSelectedPageChangeListener(e ->
        {
            vlContent.removeAll();
            vlContent.add((Component) e.getSelectedTab().getPage());
            fireToolbarChange();
        });

        tabs.add(new PagedTabs.PagedTab<>(getTranslation("main"), new MandantMainPage(presenter)));
        tabs.add(new PagedTabs.PagedTab<>(getTranslation("settings"), new MandantSettingsPage(presenter)));

        setToolbarConfig(
                () -> new ToolbarConfig.Builder()
                        .title(getTranslation("mandant"))
                        .action(getTranslation("TEST"), () -> Notification.show("TEST!"))
                        .config(tabs.getSelectedPage().getToolbarConfig())
                        .build());

        VerticalLayout root = getContent();
        root.setPadding(false);
        root.setAlignItems(FlexComponent.Alignment.STRETCH);
        root.add(tabs);
        root.addAndExpand(vlContent);
    }


    @Override
    public void beforeEnter(BeforeEnterEvent event)
    {
        super.beforeEnter(event);
        long id = getViewParameter();
        presenter.loadMandantById(id);
        tabs.getPages().forEach(MandantDetailPage::refresh);
    }
}
