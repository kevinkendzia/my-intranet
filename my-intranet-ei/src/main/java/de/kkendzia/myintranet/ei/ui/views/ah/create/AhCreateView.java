package de.kkendzia.myintranet.ei.ui.views.ah.create;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.Route;
import de.kkendzia.myintranet.ei.core.view.AbstractEIView;
import de.kkendzia.myintranet.ei.ui.components.menu.provider.annotation.MenuRoute;
import de.kkendzia.myintranet.ei.ui.components.sidebar.SidebarConfiguration;
import de.kkendzia.myintranet.ei.ui.components.sidebar.SidebarConfiguration.SidebarAction;
import de.kkendzia.myintranet.ei.ui.components.sidebar.SidebarConfiguration.SidebarHeader;
import de.kkendzia.myintranet.ei.ui.components.sidebar.SidebarConfiguration.SidebarText;
import de.kkendzia.myintranet.ei.ui.components.toolbar.ToolbarConfiguration;
import de.kkendzia.myintranet.ei.ui.layouts.main.EIMainLayout;
import de.kkendzia.myintranet.ei.ui.views.ah.create.content.CoreDataForm;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static de.kkendzia.myintranet.ei.core.i18n.TranslationKeys.COMMON;

@Route(value = "ah/new", layout = EIMainLayout.class)
@MenuRoute(label = "menu.create", parent = "ah", position = 0)
@PermitAll
public class AhCreateView extends AbstractEIView<VerticalLayout> implements AfterNavigationObserver
{
    private CoreDataForm frmCore = new CoreDataForm(getTranslation(COMMON));
//    private AdressForm frmAdress = new AdressForm(getTranslation(ADRESS));

    @Autowired
    private AhCreatePresenter presenter;

    public AhCreateView()
    {
        setToolbarConfig(
                new ToolbarConfiguration.Builder()
                        .title(getTranslation("ah.create"))
                        .action("create", this::create)
                        .build());

//        setLeftSidebarConfig(
//                new SidebarConfiguration.Builder()
//                        .header(
//                                new SidebarHeader(
//                                        "Das ist ein Test!",
//                                        "Das ist ein Test!",
//                                        List.of(new SidebarAction("Test", () -> System.out.println("TEST")))))
//                        .action(new SidebarAction("Test2", () -> System.out.println("TEST2")))
//                        .build());

        setRightSidebarConfig(
                new SidebarConfiguration.Builder()
                        .header(
                                new SidebarHeader(
                                        "Das ist ein Test!",
                                        "Das ist ein Test!",
                                        List.of(new SidebarAction("Test", () -> System.out.println("TEST")))))
                        .action(new SidebarAction("Test2", () -> System.out.println("TEST2")))
                        .text(new SidebarText(
                                "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet."))
                        .build());

        VerticalLayout root = getContent();
        root.setPadding(true);
        root.add(frmCore);
//        root.add(frmAdress);
    }

    private void create()
    {
//        AhCreatePresenter.AhData coreData = new AhCreatePresenter.AhData();
//        Adress adressData = new Adress();
////        presenter.save(new AhCreatePresenter.AhCreateRequest(coreData, adressData));
//        presenter.save(new AhCreateRequest(frmCore.getChanges(), null));

//        UI.getCurrent().navigate(AhDetailView.class, coreData.getId());
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event)
    {
        frmCore.setMandantItems(presenter.loadMandantItems());

//        AhCreateRequest request = new AhCreateRequest(new AhCreatePresenter.AhData(), null);
//        frmCore.setBean(request.coreData());
//        frmAdress.setBean(request.adressData());
    }
}
