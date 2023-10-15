package de.kkendzia.myintranet.ei.ui.views.ah.create;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import de.kkendzia.myintranet.domain.shared.Adress;
import de.kkendzia.myintranet.ei.core.i18n.TranslationKeys;
import de.kkendzia.myintranet.ei.core.view.AbstractEIView;
import de.kkendzia.myintranet.ei.ui.components.adress.AdressForm;
import de.kkendzia.myintranet.ei.ui.components.menu.provider.AnnotationItemProvider.MenuRoute;
import de.kkendzia.myintranet.ei.ui.layout.EIMainLayout;
import de.kkendzia.myintranet.ei.ui.views.ah.create.content.CoreDataForm;
import de.kkendzia.myintranet.ei.ui.views.ah.detail.AhDetailView;
import org.springframework.beans.factory.annotation.Autowired;

import static de.kkendzia.myintranet.ei.core.i18n.TranslationKeys.ADRESS;
import static de.kkendzia.myintranet.ei.core.i18n.TranslationKeys.COMMON;

@Route(value = "ah/new", layout = EIMainLayout.class)
@MenuRoute(label = "menu.create", parent = "ah/create")
public class AhCreateView extends AbstractEIView<VerticalLayout>
{
    private CoreDataForm frmCore = new CoreDataForm(getTranslation(COMMON));
    private AdressForm frmAdress = new AdressForm(getTranslation(ADRESS));

    @Autowired
    private AhCreatePresenter presenter;

    public AhCreateView()
    {

        VerticalLayout root = getContent();
        root.add(new H1("AH CREATE"));
        root.add(frmCore);
        root.add(frmAdress);
        root.add(new Button(
                getTranslation(TranslationKeys.CREATE),
                e ->
                {
                    AhCreatePresenter.AhData coreData = new AhCreatePresenter.AhData();
                    Adress adressData = new Adress();
                    presenter.save(new AhCreatePresenter.AhCreateRequest(coreData, adressData));
//                    presenter.save(new AhCreateRequest(frmCore.getChanges(), frmAdress.getChanges()));

                    UI.getCurrent().navigate(AhDetailView.class, coreData.getId());
                }));
    }
}
