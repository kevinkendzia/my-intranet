package de.kkendzia.myintranet.ei.ui.components.menu;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.function.SerializableSupplier;
import de.kkendzia.myintranet.ei.ui.components.menu.provider.annotation.AnnotationItemProvider;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

    public DrawerMenu()
    {
        VerticalLayout root = getContent();
        root.setAlignItems(STRETCH);
        root.setHeightFull();
        root.add(scroller);
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

        List<TreeItem> rootItems = itemProvider.getItems();

        if (this.rootGroupLabels)
        {
            for (TreeItem x : rootItems)
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

    private Component createSingleNavigation(String label, boolean collapsible, Collection<TreeItem> routes)
    {
        SideNav nav = new SideNav(label);
        nav.setCollapsible(collapsible);
        for (TreeItem route : routes)
        {
            SideNavItem itm = createNavItemsRecursive(route);
            nav.addItem(itm);
        }

        return nav;
    }

    private SideNavItem createNavItemsRecursive(TreeItem route)
    {
        SideNavItem itm = createSubNavItem(route);

        for (TreeItem r : route.children())
        {
            SideNavItem sub = createNavItemsRecursive(r);
            itm.addItem(sub);
        }

        return itm;
    }

    private SideNavItem createSubNavItem(TreeItem route)
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
    public interface ItemProvider extends Serializable
    {
        List<TreeItem> getItems();
    }

    public record TreeItem(
            String key,
            String label,
            SerializableSupplier<Component> iconSupplier,
            Class<? extends Component> navigationTarget,
            int position,
            List<TreeItem> children)
    {
        public TreeItem(
                String key,
                String label,
                SerializableSupplier<Component> iconSupplier,
                Class<? extends Component> navigationTarget,
                int position)
        {
            this(key, label, iconSupplier, navigationTarget, position, new ArrayList<>());
        }
    }
    //endregion
}
