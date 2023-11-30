package de.kkendzia.myintranet.ei._framework.view.mixins;

import com.vaadin.flow.function.SerializableSupplier;
import de.kkendzia.myintranet.ei._framework.view.EIView;
import de.kkendzia.myintranet.ei.ui.layouts.main.sidebar.SidebarConfiguration;

import java.util.Optional;

public interface HasRightSidebar extends EIView
{
    default RightSidebarConfigSupplier getRightSidebarConfigSupplier()
    {
        return getViewData(RightSidebarConfigSupplier.class);
    }

    default Optional<RightSidebarConfigSupplier> getOptionalRightSidebarConfigSupplier()
    {
        return Optional.ofNullable(getRightSidebarConfigSupplier());
    }

    static void setRightSidebarConfig(EIView component, SidebarConfiguration sidebarConfig)
    {
        setRightSidebarConfig(component, () -> sidebarConfig);
    }

    static void setRightSidebarConfig(EIView component, RightSidebarConfigSupplier sidebarConfigSupplier)
    {
        component.setViewData(RightSidebarConfigSupplier.class, sidebarConfigSupplier);
    }

    @FunctionalInterface
    interface RightSidebarConfigSupplier extends SerializableSupplier<SidebarConfiguration>
    {
    }
}
