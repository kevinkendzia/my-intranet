package de.kkendzia.myintranet.ei.ui.views.ah.create;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.Route;
import de.kkendzia.myintranet.domain.shared.adress.Adress;
import de.kkendzia.myintranet.ei.core.view.AbstractEIView;
import de.kkendzia.myintranet.ei.core.view.toolbar.ToolbarConfig;
import de.kkendzia.myintranet.ei.ui.components.menu.provider.AnnotationItemProvider.MenuRoute;
import de.kkendzia.myintranet.ei.ui.layout.EIMainLayout;
import de.kkendzia.myintranet.ei.ui.views.ah.create.AhCreatePresenter.AhCreateRequest;
import de.kkendzia.myintranet.ei.ui.views.ah.create.content.CoreDataForm;
import de.kkendzia.myintranet.ei.ui.views.ah.detail.AhDetailView;
import org.springframework.beans.factory.annotation.Autowired;

import static de.kkendzia.myintranet.ei.core.i18n.TranslationKeys.COMMON;

@Route(value = "ah/new", layout = EIMainLayout.class)
@MenuRoute(label = "menu.create", parent = "ah", position = 0)
public class AhCreateView extends AbstractEIView<VerticalLayout> implements AfterNavigationObserver
{
    private CoreDataForm frmCore = new CoreDataForm(getTranslation(COMMON));
//    private AdressForm frmAdress = new AdressForm(getTranslation(ADRESS));

    @Autowired
    private AhCreatePresenter presenter;

    public AhCreateView()
    {
        setToolbarConfig(
                new ToolbarConfig.Builder()
                        .title(getTranslation("ah.create"))
                        .action("create", this::create)
                        .build());

        VerticalLayout root = getContent();
        root.setPadding(false);
        root.add(frmCore);
//        root.add(frmAdress);
    }

    private void create()
    {
        AhCreatePresenter.AhData coreData = new AhCreatePresenter.AhData();
        Adress adressData = new Adress();
//        presenter.save(new AhCreatePresenter.AhCreateRequest(coreData, adressData));
        presenter.save(new AhCreateRequest(frmCore.getChanges(), null));

        UI.getCurrent().navigate(AhDetailView.class, coreData.getId());
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event)
    {
        frmCore.setMandantItems(presenter.loadMandantItems());

        AhCreateRequest request = new AhCreateRequest(new AhCreatePresenter.AhData(), null);
        frmCore.setBean(request.coreData());
//        frmAdress.setBean(request.adressData());
    }
}
