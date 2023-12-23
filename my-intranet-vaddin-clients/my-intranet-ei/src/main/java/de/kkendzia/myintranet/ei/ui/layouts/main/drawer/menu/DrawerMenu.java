package de.kkendzia.myintranet.ei.ui.layouts.main.drawer.menu;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.function.SerializableSupplier;
import com.vaadin.flow.theme.lumo.LumoUtility.Overflow;
import de.kkendzia.myintranet.ei.ui.layouts.main.drawer.menu.provider.annotation.AnnotationItemProvider;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

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
        root.addClassName(Overflow.HIDDEN);
        root.setPadding(false);
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

        List<TreeItem> rootItems = itemProvider.collectItems();

        if (this.rootGroupLabels)
        {
            for (TreeItem x : rootItems)
            {
                Component nav = createSingleNavigation(
                        x.needsTranslation() ? getTranslation(x.label()) : x.label(),
                        rootGroupCollapsible,
                        x.children());
                vlNav.add(nav);
            }
        }
        else
        {
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
            SideNavItem itm = walkBranch(route);
            nav.addItem(itm);
        }

        return nav;
    }

    public SideNavItem createItem(TreeItem item)
    {
        return new SideNavItem(
                item.needsTranslation() ? getTranslation(item.label()) : item.label(),
                item.navigationTarget(),
                item.optionalIconSupplier().map(Supplier::get).orElse(null));
    }

    private SideNavItem walkBranch(TreeItem route)
    {
        SideNavItem itm = createItem(route);

        for (TreeItem r : route.children())
        {
            SideNavItem sub = walkBranch(r);
            itm.addItem(sub);
        }

        return itm;
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
        List<TreeItem> collectItems();
    }

    public record TreeItem(
            String key,
            String label,
            boolean needsTranslation,
            SerializableSupplier<Component> iconSupplier,
            Class<? extends Component> navigationTarget,
            int position,
            List<TreeItem> children)
    {
        public TreeItem(
                String key,
                String label,
                boolean needsTranslation,
                SerializableSupplier<Component> iconSupplier,
                Class<? extends Component> navigationTarget,
                int position)
        {
            this(key, label, needsTranslation, iconSupplier, navigationTarget, position, new ArrayList<>());
        }

        public Optional<SerializableSupplier<Component>> optionalIconSupplier()
        {
            return Optional.ofNullable(iconSupplier());
        }
    }
    //endregion
}
