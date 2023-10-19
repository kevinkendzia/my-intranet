package de.kkendzia.myintranet.ei.core.view.sidebar;

import com.vaadin.flow.component.Component;
import de.kkendzia.myintranet.ei.core.view.EIView;

import java.util.Optional;

import static com.vaadin.flow.component.ComponentUtil.getData;
import static com.vaadin.flow.component.ComponentUtil.setData;

public interface HasSidebarConfig extends EIView
{
    default SidebarConfig getSidebarConfig()
    {
        return getOptionalSidebarConfig().orElse(null);
    }

    default Optional<SidebarConfig> getOptionalSidebarConfig()
    {
        SidebarConfig.SidebarConfigSupplier supplier =
                getData((Component) this, SidebarConfig.SidebarConfigSupplier.class);
        return Optional.ofNullable(supplier).map(SidebarConfig.SidebarConfigSupplier::getSidebarConfig);
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
