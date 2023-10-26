package de.kkendzia.myintranet.ei.core.view.mixins;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasElement;
import de.kkendzia.myintranet.ei.ui.components.toolbar.ToolbarConfiguration;
import de.kkendzia.myintranet.ei.ui.components.toolbar.ToolbarConfiguration.ToolbarConfigSupplier;

import java.util.Optional;

import static com.vaadin.flow.component.ComponentUtil.getData;
import static com.vaadin.flow.component.ComponentUtil.setData;

public interface HasToolbarConfig extends HasElement
{
    default ToolbarConfigSupplier getToolbarConfigSupplier()
    {
        return getData((Component) this, ToolbarConfigSupplier.class);
    }
    default Optional<ToolbarConfigSupplier> getOptionalToolbarConfigSupplier()
    {
        return Optional.ofNullable(getToolbarConfigSupplier());
    }

    default ToolbarConfiguration getToolbarConfig()
    {
        return getOptionalToolbarConfig().orElse(null);
    }

    default Optional<ToolbarConfiguration> getOptionalToolbarConfig()
    {
        ToolbarConfigSupplier supplier = getToolbarConfigSupplier();
        return Optional.ofNullable(supplier).map(ToolbarConfigSupplier::get);
    }

    static void setToolbarConfig(Component component, ToolbarConfiguration toolbarConfiguration)
    {
        setToolbarConfig(component, () -> toolbarConfiguration);
    }

    static void setToolbarConfig(Component component, ToolbarConfigSupplier toolbarConfigSupplier)
    {
        setData(component, ToolbarConfigSupplier.class, toolbarConfigSupplier);
    }
}
