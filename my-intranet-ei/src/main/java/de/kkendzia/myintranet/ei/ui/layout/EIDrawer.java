package de.kkendzia.myintranet.ei.ui.layout;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;
import de.kkendzia.myintranet.ei.core.EIComponent;
import de.kkendzia.myintranet.ei.ui.components.menu.DrawerMenu;
import de.kkendzia.myintranet.ei.ui.components.menu.provider.AnnotationItemProvider;

import java.util.List;

import static de.kkendzia.myintranet.ei.core.i18n.TranslationKeys.APP_TITLE;

public final class EIDrawer extends EIComponent<VerticalLayout>
{
    public static final String PARENT_OTHER = "other";

    public EIDrawer()
    {
        DrawerMenu menu =
                new DrawerMenu(
                        new EIDrawerHeader(getTranslation(APP_TITLE)),
                        new DrawerMenu.MenuFooter());
        menu.setItemProvider(
                new AnnotationItemProvider(
                        List.of(
                                new DrawerMenu.DrawerMenuItem(
                                        "nav",
                                        getTranslation("menu.target.nav")),
                                new DrawerMenu.DrawerMenuItem(
                                        "ah",
                                        getTranslation("menu.target.ah")),
                                new DrawerMenu.DrawerMenuItem(
                                        PARENT_OTHER,
                                        getTranslation("menu.target.other")))));
        VerticalLayout root = getContent();
        root.setPadding(false);
        root.addAndExpand(menu);
    }

    public static class EIDrawerHeader extends EIComponent<Header>
    {
        public EIDrawerHeader(String title)
        {
            H1 appName = new H1(title);
            appName.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);

            Header header = getContent();
            header.setHeight("10em");
            header.add(appName);
        }
    }
}
