package de.kkendzia.myintranet.ei.core.view.mixins;

import com.vaadin.flow.component.Component;
import de.kkendzia.myintranet.ei.core.view.EIView;
import de.kkendzia.myintranet.ei.ui.components.sidebar.SidebarConfig;

import java.util.Optional;

import static com.vaadin.flow.component.ComponentUtil.getData;
import static com.vaadin.flow.component.ComponentUtil.setData;

public interface HasSidebarConfig extends EIView
{
    default SidebarConfig.SidebarConfigSupplier getSidebarConfigSupplier()
    {
        return getData((Component) this, SidebarConfig.SidebarConfigSupplier.class);
    }
    default Optional<SidebarConfig.SidebarConfigSupplier> getOptionalSidebarConfigSupplier()
    {
        return Optional.ofNullable(getSidebarConfigSupplier());
    }

    default SidebarConfig getSidebarConfig()
    {
        return getOptionalSidebarConfig().orElse(null);
    }

    default Optional<SidebarConfig> getOptionalSidebarConfig()
    {
        SidebarConfig.SidebarConfigSupplier supplier = getSidebarConfigSupplier();
        return Optional.ofNullable(supplier).map(SidebarConfig.SidebarConfigSupplier::get);
    }

    static void setSidebarConfig(Component component, SidebarConfig sidebarConfig)
    {
        setSidebarConfig(component, () -> sidebarConfig);
    }

    static void setSidebarConfig(Component component, SidebarConfig.SidebarConfigSupplier sidebarConfigSupplier)
    {
        setData(component, SidebarConfig.SidebarConfigSupplier.class, sidebarConfigSupplier);
    }
}
