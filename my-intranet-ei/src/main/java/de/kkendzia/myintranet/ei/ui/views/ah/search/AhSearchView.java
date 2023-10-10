package de.kkendzia.myintranet.ei.ui.views.ah.search;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import de.kkendzia.myintranet.ei.ui._framework.view.AbstractEIView;
import de.kkendzia.myintranet.ei.ui.components.menu.provider.AnnotationItemProvider;
import de.kkendzia.myintranet.ei.ui.layout.EIMainLayout;

@AnnotationItemProvider.MenuRoute(label = "menu.search", parent = "ah/search")
@Route(value = "ah/search", layout = EIMainLayout.class)
public class AhSearchView
        extends AbstractEIView<VerticalLayout>
{
    public AhSearchView()
    {
        VerticalLayout root = getContent();
        root.add(new H1("AH SEARCH"));
    }
}
