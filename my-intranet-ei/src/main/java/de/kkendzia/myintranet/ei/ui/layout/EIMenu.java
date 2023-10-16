package de.kkendzia.myintranet.ei.ui.layout;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import de.kkendzia.myintranet.ei.core.EIComponent;
import de.kkendzia.myintranet.ei.ui.components.menu.DrawerMenu;
import de.kkendzia.myintranet.ei.ui.components.menu.provider.AnnotationItemProvider;

import java.util.List;

public final class EIMenu extends EIComponent<VerticalLayout>
{
    public EIMenu()
    {
        DrawerMenu menu =
                new DrawerMenu(
                        new DrawerMenu.MenuHeader(),
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
                                        "other",
                                        getTranslation("menu.target.other")))));
        VerticalLayout root = getContent();
        root.addAndExpand(menu);
    }
}
