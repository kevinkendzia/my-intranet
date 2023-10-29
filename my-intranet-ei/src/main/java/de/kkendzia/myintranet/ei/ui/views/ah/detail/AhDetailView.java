package de.kkendzia.myintranet.ei.ui.views.ah.detail;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.Route;
import de.kkendzia.myintranet.domain.ah.Ah;
import de.kkendzia.myintranet.ei.core.parameters.HasViewParameter;
import de.kkendzia.myintranet.ei.core.view.AbstractEIView;
import de.kkendzia.myintranet.ei.ui.components.async.AsyncContainer;
import de.kkendzia.myintranet.ei.ui.components.sidebar.SidebarConfiguration;
import de.kkendzia.myintranet.ei.ui.components.sidebar.SidebarConfiguration.SidebarAction;
import de.kkendzia.myintranet.ei.ui.components.toolbar.ToolbarConfiguration;
import de.kkendzia.myintranet.ei.ui.components.toolbar.ToolbarConfiguration.ToolbarAction;
import de.kkendzia.myintranet.ei.ui.layouts.main.EIMainLayout;
import de.kkendzia.myintranet.ei.ui.views.ah.detail.content.DummyPanel;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;

import static com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment.STRETCH;
import static de.kkendzia.myintranet.ei.core.i18n.TranslationKeys.SAVE;

@Route(value = "ah", layout = EIMainLayout.class)
@PermitAll
public class AhDetailView
        extends AbstractEIView<VerticalLayout>
        implements HasViewParameter<Long>, AfterNavigationObserver
{
    private final H1 hTitle = new H1("AH CREATE");
    private final AhDetailPresenter presenter;

    @Autowired
    public AhDetailView(final AhDetailPresenter presenter)
    {
        this.presenter = presenter;

        setLeftSidebarConfig(
                new SidebarConfiguration.Builder()
                        .action(new SidebarAction(getTranslation(SAVE), presenter::save))
                        .build());

        setToolbarConfig(
                new ToolbarConfiguration.Builder()
                        .action(new ToolbarAction(getTranslation(SAVE), presenter::save))
                        .build());

        VerticalLayout root = getContent();
        root.setAlignItems(STRETCH);
        root.add(hTitle);
//        root.add(new CoreDataPanel());
        root.add(new AsyncContainer<>(new DummyPanel(), (x, y) -> testWait(x, y)));
        root.add(new AsyncContainer<>(new DummyPanel(), (x, y) -> testWait(x, y)));
        root.add(new AsyncContainer<>(new DummyPanel(), (x, y) -> testWait(x, y)));
        root.add(new AsyncContainer<>(new DummyPanel(), (x, y) -> testWait(x, y)));
        root.add(new AsyncContainer<>(new DummyPanel(), (x, y) -> testWait(x, y)));
        root.add(new AsyncContainer<>(new DummyPanel(), (x, y) -> testWait(x, y)));
    }

    private static void testWait(
            DummyPanel panel,
            UI ui)
    {
        try
        {
            Thread.sleep(5000);
            ui.access(panel::setLoaded);
        }
        catch (InterruptedException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void beforeEnterView(BeforeEnterEvent event)
    {

        long id = getViewParameter();
        Ah ah = presenter.loadAhById(id);
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event)
    {
        long id = getViewParameter();
        hTitle.setText(hTitle.getText() + id);
    }
}
