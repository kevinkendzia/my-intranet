package de.kkendzia.myintranet.ei.ui.views.ah.detail;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.Route;
import de.kkendzia.myintranet.domain.ah.Ah.AhID;
import de.kkendzia.myintranet.ei.core.i18n.TranslationKeys;
import de.kkendzia.myintranet.ei.core.parameters.HasViewParameter;
import de.kkendzia.myintranet.ei.core.view.AbstractEIView;
import de.kkendzia.myintranet.ei.ui.components.tabs.PagedTabs;
import de.kkendzia.myintranet.ei.ui.components.toolbar.ToolbarConfiguration;
import de.kkendzia.myintranet.ei.ui.components.toolbar.ToolbarConfiguration.ToolbarAction;
import de.kkendzia.myintranet.ei.ui.layouts.TabsLayout;
import de.kkendzia.myintranet.ei.ui.layouts.main.EIMainLayout;
import de.kkendzia.myintranet.ei.ui.layouts.main.sidebar.SidebarConfiguration;
import de.kkendzia.myintranet.ei.ui.layouts.main.sidebar.SidebarConfiguration.SidebarAction;
import de.kkendzia.myintranet.ei.ui.views.ah.detail.pages.AhCoreDataPage;
import de.kkendzia.myintranet.ei.ui.views.ah.detail.pages.AhDetailPage;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static de.kkendzia.myintranet.ei.core.i18n.TranslationKeys.COMMON;
import static de.kkendzia.myintranet.ei.core.i18n.TranslationKeys.SAVE;

@Route(value = "ah", layout = EIMainLayout.class)
@PermitAll
public class AhDetailView
        extends AbstractEIView<TabsLayout<AhDetailPage>>
        implements HasViewParameter<String>, AfterNavigationObserver
{
    private final H1 hTitle = new H1("AH CREATE");
    private final AhDetailPresenter presenter;

    @Autowired
    public AhDetailView(final AhDetailPresenter presenter)
    {
        this.presenter = presenter;

        final var header = new SidebarConfiguration.SidebarHeader(
                getTranslation(TranslationKeys.CREATE),
                getTranslation(TranslationKeys.Examples.LOREM_S));

        setLeftSidebarConfig(
                new SidebarConfiguration.Builder()
                        .header(header)
                        .action(new SidebarAction(getTranslation(SAVE), presenter::save))
                        .build());

        setRightSidebarConfig(
                new SidebarConfiguration.Builder()
                        .header(header)
                        .text(TranslationKeys.Examples.LOREM_L)
                        .build());

        setToolbarConfig(
                new ToolbarConfiguration.Builder()
                        .action(new ToolbarAction(getTranslation(SAVE), presenter::save))
                        .build());

        TabsLayout<AhDetailPage> root = getContent();
        root.add(new PagedTabs.PagedTab<>(getTranslation(COMMON), new AhCoreDataPage(presenter)));
    }

    @Override
    public void beforeEnterView(BeforeEnterEvent event)
    {
        String id = getViewParameter();
        presenter.loadAhById(new AhID(UUID.fromString(id)));
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event)
    {
        String id = getViewParameter();
        hTitle.setText(hTitle.getText() + id);
    }
}
