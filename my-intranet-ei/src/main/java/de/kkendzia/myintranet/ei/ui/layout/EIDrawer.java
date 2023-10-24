package de.kkendzia.myintranet.ei.ui.layout;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;
import de.kkendzia.myintranet.ei.core.EIComponent;
import de.kkendzia.myintranet.ei.core.i18n.TranslationKeys;
import de.kkendzia.myintranet.ei.ui.components.menu.DrawerMenu;
import de.kkendzia.myintranet.ei.ui.components.menu.DrawerMenu.DrawerMenuItem;
import de.kkendzia.myintranet.ei.ui.components.menu.provider.AnnotationItemProvider;

import java.util.List;

import static de.kkendzia.myintranet.ei.core.i18n.TranslationKeys.APP_TITLE;

public final class EIDrawer extends EIComponent<VerticalLayout>
{
    public EIDrawer()
    {
        DrawerMenu menu =
                new DrawerMenu(
                        new EIDrawerHeader(getTranslation(APP_TITLE)),
                        new DrawerMenu.MenuFooter());
        menu.setItemProvider(
                new AnnotationItemProvider(
                        List.of(
                                new DrawerMenuItem(
                                        EIMenuKeys.START,
                                        getTranslation(TranslationKeys.START)),
                                new DrawerMenuItem(
                                        EIMenuKeys.MANDANTEN,
                                        getTranslation(TranslationKeys.MANDANTEN)),
                                new DrawerMenuItem(
                                        EIMenuKeys.AH,
                                        getTranslation(TranslationKeys.AHS)),
                                new DrawerMenuItem(
                                        EIMenuKeys.ADMIN,
                                        getTranslation(TranslationKeys.ADMIN),
                                        new DrawerMenuItem(
                                                EIMenuKeys.USER,
                                                getTranslation(TranslationKeys.USERS)),
                                        new DrawerMenuItem(
                                                EIMenuKeys.ROLE,
                                                getTranslation(TranslationKeys.ROLES)),
                                        new DrawerMenuItem(
                                                EIMenuKeys.PERMISSION,
                                                getTranslation(TranslationKeys.PERMISSIONS))),
                                new DrawerMenuItem(
                                        EIMenuKeys.OTHER,
                                        getTranslation(TranslationKeys.OTHER)))));
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

    public static final class EIMenuKeys
    {
        public static final String START = "start";
        public static final String MANDANTEN = "mandant";
        public static final String AH = "ah";
        public static final String OTHER = "other";
        public static final String ADMIN = "admin";
        public static final String USER = "user";
        public static final String ROLE = "role";
        public static final String PERMISSION = "permission";

        private EIMenuKeys()
        {
            // No Instance!
        }
    }
}
