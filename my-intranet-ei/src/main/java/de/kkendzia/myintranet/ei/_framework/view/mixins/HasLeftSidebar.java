package de.kkendzia.myintranet.ei._framework.view.mixins;

import com.vaadin.flow.function.SerializableSupplier;
import de.kkendzia.myintranet.ei._framework.view.EIView;
import de.kkendzia.myintranet.ei.ui.layouts.main.sidebar.SidebarConfiguration;

import java.util.Optional;

public interface HasLeftSidebar extends EIView
{
    default LeftSidebarConfigSupplier getLeftSidebarConfigSupplier()
    {
        return getViewData(LeftSidebarConfigSupplier.class);
    }

    default Optional<LeftSidebarConfigSupplier> getOptionalLeftSidebarConfigSupplier()
    {
        return Optional.ofNullable(getLeftSidebarConfigSupplier());
    }

    static void setLeftSidebarConfig(EIView component, SidebarConfiguration sidebarConfig)
    {
        setLeftSidebarConfig(component, () -> sidebarConfig);
    }

    static void setLeftSidebarConfig(EIView component, LeftSidebarConfigSupplier sidebarConfigSupplier)
    {
        component.setViewData(LeftSidebarConfigSupplier.class, sidebarConfigSupplier);
    }

    @FunctionalInterface
    interface LeftSidebarConfigSupplier extends SerializableSupplier<SidebarConfiguration>
    {
    }
}
