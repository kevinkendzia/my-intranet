package de.kkendzia.myintranet.ei.ui.views.home;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import de.kkendzia.myintranet.ei.ui._framework.view.AbstractEIView;
import de.kkendzia.myintranet.ei.ui.components.menu.provider.AnnotationItemProvider.MenuRoute;
import de.kkendzia.myintranet.ei.ui.layout.EIMainLayout;

import static de.kkendzia.myintranet.ei.ui.constants.EITheme.Image.IMG_HOME;

@Route(value = "home", layout = EIMainLayout.class)
@RouteAlias(value = "", layout = EIMainLayout.class)
@MenuRoute(label = "menu.home", parent = "nav", position = 0)
public class HomeView extends AbstractEIView<VerticalLayout> implements LocaleChangeObserver
{
    public HomeView()
    {
        VerticalLayout vlText = new VerticalLayout();
        vlText.add(new H2(getTranslation("app.title.pre")));
        vlText.add(new H1(getTranslation("app.title")));
        vlText.add(new Paragraph(getTranslation("app.description")));

        Image imgHome = new Image(IMG_HOME.getPath(), "HOME");
        imgHome.addClassNames("home-image");
        imgHome.setWidth("25em");

        HorizontalLayout hlCenter = new HorizontalLayout();
        hlCenter.add(vlText);
        hlCenter.add(imgHome);

        VerticalLayout root = getContent();
        root.setAlignItems(Alignment.CENTER);
        root.setJustifyContentMode(JustifyContentMode.CENTER);
        root.setHeightFull();
        root.add(hlCenter);
    }

    @Override
    public void localeChange(LocaleChangeEvent localeChangeEvent)
    {
        // TODO
        setPageTitle(getTranslation("home.title"));
    }
}
