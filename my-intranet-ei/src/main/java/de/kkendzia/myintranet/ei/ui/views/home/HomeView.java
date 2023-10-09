package de.kkendzia.myintranet.ei.ui.views.home;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import de.kkendzia.myintranet.ei.ui._framework.view.AbstractEIView;
import de.kkendzia.myintranet.ei.ui.layout.EIMainLayout;
import de.kkendzia.myintranet.ei.ui.layout.menu.MenuRoute;

@MenuRoute(label = "menu.home")
@Route(value = "home", layout = EIMainLayout.class)
@RouteAlias(value = "", layout = EIMainLayout.class)
public class HomeView extends AbstractEIView<VerticalLayout>
{
    public HomeView()
    {
        VerticalLayout root = getContent();
        root.add(new H2(getTranslation("app.title.pre")));
        root.add(new H1(getTranslation("app.title")));
        root.add(new Paragraph(getTranslation("app.description")));
    }
}
