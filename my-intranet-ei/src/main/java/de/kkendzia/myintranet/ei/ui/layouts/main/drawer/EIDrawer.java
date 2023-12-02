package de.kkendzia.myintranet.ei.ui.layouts.main.drawer;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.auth.AccessAnnotationChecker;
import de.kkendzia.myintranet.ei.core.i18n.TranslationKeys;
import de.kkendzia.myintranet.ei.ui.layouts.main.drawer.menu.DrawerMenu;
import de.kkendzia.myintranet.ei.ui.layouts.main.drawer.menu.provider.MenuDefinition;
import de.kkendzia.myintranet.ei.ui.layouts.main.drawer.menu.provider.annotation.AnnotationItemProvider;
import org.springframework.boot.info.BuildProperties;

import static de.kkendzia.myintranet.ei.core.i18n.TranslationKeys.APP_TITLE;
import static de.kkendzia.myintranet.ei.core.i18n.TranslationKeys.AhKeys.AHS;
import static de.kkendzia.myintranet.ei.core.i18n.TranslationKeys.MENU;
import static de.kkendzia.myintranet.ei.core.i18n.TranslationKeys.MandantKeys.MANDANTEN;

public final class EIDrawer extends Composite<VerticalLayout>
{
    public EIDrawer(AccessAnnotationChecker accessAnnotationChecker, BuildProperties buildProperties)
    {
        AnnotationItemProvider itemProvider = new AnnotationItemProvider();
        itemProvider.setRouteFilter(r -> accessAnnotationChecker.hasAccess(r.getNavigationTarget()));
        itemProvider.setMenuStructure(
                new MenuDefinition(
                        EIMenuKeys.START,
                        getTranslation(TranslationKeys.START)),
                new MenuDefinition(
                        EIMenuKeys.MANDANTEN,
                        getTranslation(MANDANTEN)),
                new MenuDefinition(
                        EIMenuKeys.AH,
                        getTranslation(AHS)),
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
        menu.setRootGroupCollapsible(false);
        menu.setRootGroupLabels(false);
        menu.setItemProvider(itemProvider);

        VerticalLayout root = getContent();
        root.setPadding(true);
        root.setAlignItems(Alignment.STRETCH);
        root.add(new EIDrawerHeader(
                getTranslation(APP_TITLE),
                getTranslation(MENU)));
        root.addAndExpand(menu);
        root.add(new EIDrawerFooter(buildProperties));
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
