package de.kkendzia.myintranet.ei.core.view.toolbar;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasElement;
import de.kkendzia.myintranet.ei.core.view.toolbar.ToolbarConfig.ToolbarConfigSupplier;

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

    default ToolbarConfig getToolbarConfig()
    {
        return getOptionalToolbarConfig().orElse(null);
    }

    default Optional<ToolbarConfig> getOptionalToolbarConfig()
    {
        ToolbarConfigSupplier supplier = getToolbarConfigSupplier();
        return Optional.ofNullable(supplier).map(ToolbarConfigSupplier::get);
    }

    static void setToolbarConfig(Component component, ToolbarConfig toolbarConfig)
    {
        setToolbarConfig(component, () -> toolbarConfig);
    }

    static void setToolbarConfig(Component component, ToolbarConfigSupplier toolbarConfigSupplier)
    {
        setData(component, ToolbarConfigSupplier.class, toolbarConfigSupplier);
    }
}
