package de.kkendzia.myintranet.ei.ui.views.ah.create;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import de.kkendzia.myintranet.ei.ui._framework.view.AbstractEIView;
import de.kkendzia.myintranet.ei.ui.layout.EIMainLayout;
import de.kkendzia.myintranet.ei.ui.layout.menu.MenuRoute;
import org.springframework.beans.factory.annotation.Autowired;

@MenuRoute(label = "menu.create", group = "ah/create")
@Route(value = "ah/new", layout = EIMainLayout.class)
public class AhCreateView extends AbstractEIView<VerticalLayout>
{
    private TextField txtMatchcode = new TextField();

    @Autowired
    private AhCreatePresenter presenter;
    public AhCreateView()
    {
        VerticalLayout root = getContent();
        root.add(new H1("AH CREATE"));
        root.add(txtMatchcode);
        root.add(new Button(getTranslation("next"), e -> presenter.doStuff()));
    }
}
