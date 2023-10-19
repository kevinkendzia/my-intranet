package de.kkendzia.myintranet.ei.core.view.toolbar;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasElement;
import de.kkendzia.myintranet.ei.core.view.toolbar.ToolbarConfig.ToolbarConfigSupplier;

import java.util.Optional;

import static com.vaadin.flow.component.ComponentUtil.getData;
import static com.vaadin.flow.component.ComponentUtil.setData;

public interface HasToolbarConfig extends HasElement
{
    default ToolbarConfig getToolbarConfig()
    {
        return getOptionalToolbarConfig().orElse(null);
    }

    default Optional<ToolbarConfig> getOptionalToolbarConfig()
    {
        ToolbarConfigSupplier supplier =
                getData((Component) this, ToolbarConfigSupplier.class);
        return Optional.ofNullable(supplier).map(ToolbarConfigSupplier::getToolbarConfig);
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
