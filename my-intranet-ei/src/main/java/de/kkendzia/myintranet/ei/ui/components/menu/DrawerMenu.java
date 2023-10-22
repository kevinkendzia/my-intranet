package de.kkendzia.myintranet.ei.ui.components.menu;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.function.SerializableSupplier;
import com.vaadin.flow.theme.lumo.LumoUtility.FontSize;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import de.kkendzia.myintranet.ei.core.EIComponent;
import de.kkendzia.myintranet.ei.ui.components.menu.provider.AnnotationItemProvider;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.StringJoiner;

import static com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment.STRETCH;
import static java.util.Objects.requireNonNull;

public class DrawerMenu
        extends Composite<VerticalLayout>
{
    private final Scroller scroller = new Scroller();

    // CONFIG
    private ItemProvider itemProvider = new AnnotationItemProvider();
    private boolean rootGroupLabels = true;
    private boolean rootGroupCollapsible = true;

    public DrawerMenu(
            Component header,
            Component footer)
    {
        VerticalLayout root = getContent();
        root.setAlignItems(STRETCH);
        root.setHeightFull();

        if (header != null)
        {
            root.add(header);
        }

        root.add(scroller);

        if (footer != null)
        {
            root.add(footer);
        }
    }

    @Override
    protected void onAttach(AttachEvent attachEvent)
    {
        super.onAttach(attachEvent);
        Component navigation = createNavigation();
        scroller.setContent(navigation);
    }

    private Component createNavigation()
    {
        VerticalLayout vlNav = new VerticalLayout();
        vlNav.setPadding(false);
        vlNav.setSpacing(true);
        vlNav.setAlignItems(STRETCH);

        List<DrawerMenuItem> rootItems = itemProvider.getItems();

        if (this.rootGroupLabels)
        {
            for (DrawerMenuItem x : rootItems)
            {
                Component nav = createSingleNavigation(x.label(), rootGroupCollapsible, x.children());
                vlNav.add(nav);
            }
        }
        else
        {
            // TODO: remove collapsible?
            Component nav = createSingleNavigation(null, false, rootItems);
            vlNav.add(nav);
        }

        return vlNav;
    }

    private Component createSingleNavigation(String label, boolean collapsible, Collection<DrawerMenuItem> routes)
    {
        SideNav nav = new SideNav(label);
        nav.setCollapsible(collapsible);
        for (DrawerMenuItem route : routes)
        {
            SideNavItem itm = createNavItemsRecursive(route);
            nav.addItem(itm);
        }

        return nav;
    }

    private SideNavItem createNavItemsRecursive(DrawerMenuItem route)
    {
        SideNavItem itm = createSubNavItem(route);

        for (DrawerMenuItem r : route.children())
        {
            SideNavItem sub = createNavItemsRecursive(r);
            itm.addItem(sub);
        }

        return itm;
    }

    private SideNavItem createSubNavItem(DrawerMenuItem route)
    {

        return new SideNavItem(
                getTranslation(route.label()),
                route.navigationTarget(),
                route.iconSupplier() != null ? route.iconSupplier().get() : null);
    }

    //region SETTER / GETTER
    public void setRootGroupLabels(boolean rootGroupLabels)
    {
        this.rootGroupLabels = rootGroupLabels;
    }

    public void setRootGroupCollapsible(boolean rootGroupCollapsible)
    {
        this.rootGroupCollapsible = rootGroupCollapsible;
    }

    public void setItemProvider(ItemProvider itemProvider)
    {
        this.itemProvider = requireNonNull(itemProvider, "itemProvider can't be null!");
    }
    //endregion

    //region TYPES
    public static class MenuHeader extends EIComponent<Header>
    {
        public MenuHeader(String title)
        {
            H1 appName = new H1(title);
            appName.addClassNames(FontSize.LARGE, Margin.NONE);

            Header header = getContent();
            header.setHeight("10em");
            header.add(appName);
        }
    }

    public static class MenuFooter extends EIComponent<Footer>
    {

    }

    public interface ItemProvider extends Serializable
    {
        List<DrawerMenuItem> getItems();
    }

    public record DrawerMenuItem(
            String key,
            String label,
            SerializableSupplier<Component> iconSupplier,
            Class<? extends Component> navigationTarget,
            int position,
            List<DrawerMenuItem> children)
    {
        public DrawerMenuItem(String key, String label)
        {
            this(key, label, null, null, Integer.MAX_VALUE, new ArrayList<>());
        }

        public static DrawerMenuItem group(String key,
                                           String label,
                                           SerializableSupplier<Component> iconSupplier,
                                           int position)
        {
            return new DrawerMenuItem(
                    key,
                    label,
                    iconSupplier,
                    null,
                    position,
                    new ArrayList<>());
        }

        @Override
        public String toString()
        {
            return new StringJoiner(", ", DrawerMenuItem.class.getSimpleName() + "[", "]")
                    .add("key='" + key + "'")
                    .toString();
        }
    }
    //endregion
}
