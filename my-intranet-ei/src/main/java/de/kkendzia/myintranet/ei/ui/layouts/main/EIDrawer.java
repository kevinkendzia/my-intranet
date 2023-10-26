package de.kkendzia.myintranet.ei.ui.layouts.main;

import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.auth.AccessAnnotationChecker;
import com.vaadin.flow.theme.lumo.LumoUtility.FontSize;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import de.kkendzia.myintranet.ei.core.EIComponent;
import de.kkendzia.myintranet.ei.core.i18n.TranslationKeys;
import de.kkendzia.myintranet.ei.ui.components.menu.DrawerMenu;
import de.kkendzia.myintranet.ei.ui.components.menu.provider.MenuDefinition;
import de.kkendzia.myintranet.ei.ui.components.menu.provider.annotation.AnnotationItemProvider;

import static de.kkendzia.myintranet.ei.core.i18n.TranslationKeys.APP_TITLE;

public final class EIDrawer extends EIComponent<VerticalLayout>
{
    public EIDrawer(AccessAnnotationChecker accessAnnotationChecker)
    {
        AnnotationItemProvider itemProvider = new AnnotationItemProvider();
        itemProvider.setRouteFilter(r -> accessAnnotationChecker.hasAccess(r.getNavigationTarget()));
        itemProvider.setMenuStructure(
                new MenuDefinition(
                        EIMenuKeys.START,
                        getTranslation(TranslationKeys.START)),
                new MenuDefinition(
                        EIMenuKeys.MANDANTEN,
                        getTranslation(TranslationKeys.MANDANTEN)),
                new MenuDefinition(
                        EIMenuKeys.AH,
                        getTranslation(TranslationKeys.AHS)),
                new MenuDefinition(
                        EIMenuKeys.ADMIN,
                        getTranslation(TranslationKeys.ADMIN),
                        new MenuDefinition(
                                EIMenuKeys.USER,
                                getTranslation(TranslationKeys.USERS)),
                        new MenuDefinition(
                                EIMenuKeys.ROLE,
                                getTranslation(TranslationKeys.ROLES)),
                        new MenuDefinition(
                                EIMenuKeys.PERMISSION,
                                getTranslation(TranslationKeys.PERMISSIONS))),
                new MenuDefinition(
                        EIMenuKeys.OTHER,
                        getTranslation(TranslationKeys.OTHER)));
        itemProvider.setFallbackKey(EIMenuKeys.OTHER);

        DrawerMenu menu = new DrawerMenu();
        menu.setItemProvider(itemProvider);

        VerticalLayout root = getContent();
        root.setPadding(false);
        root.add(new EIDrawerHeader(getTranslation(APP_TITLE)));
        root.addAndExpand(menu);
        root.add(new EIDrawerFooter());
    }

    public static class EIDrawerHeader extends EIComponent<Header>
    {
        public EIDrawerHeader(String title)
        {
            H1 appName = new H1(title);
            appName.addClassNames(FontSize.LARGE, Margin.NONE);

            Header header = getContent();
            header.setHeight("10em");
            header.add(appName);
        }
    }

    public static class EIDrawerFooter extends EIComponent<Footer>
    {
        public EIDrawerFooter()
        {
            Footer root = getContent();
            root.add(new Span("Version"));
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
