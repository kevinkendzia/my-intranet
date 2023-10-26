package de.kkendzia.myintranet.ei.core.view.page;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import de.kkendzia.myintranet.ei.core.view.mixins.HasSidebarConfig;
import de.kkendzia.myintranet.ei.ui.components.sidebar.SidebarConfig;
import de.kkendzia.myintranet.ei.ui.components.sidebar.ConfigurableSidebarNotifier;
import de.kkendzia.myintranet.ei.core.view.mixins.HasToolbarConfig;
import de.kkendzia.myintranet.ei.ui.components.toolbar.ToolbarConfiguration;
import de.kkendzia.myintranet.ei.ui.components.toolbar.ConfigurableToolbarNotifier;

public abstract class AbstractPage<C extends Component> extends Composite<C>
        implements HasToolbarConfig, ConfigurableToolbarNotifier, HasSidebarConfig, ConfigurableSidebarNotifier, EIPage
{
    protected void setToolbarConfig(ToolbarConfiguration toolbarConfiguration)
    {
        HasToolbarConfig.setToolbarConfig(this, toolbarConfiguration);
    }

    protected void setToolbarConfig(ToolbarConfiguration.ToolbarConfigSupplier toolbarConfigSupplier)
    {
        HasToolbarConfig.setToolbarConfig(this, toolbarConfigSupplier);
    }

    protected void setSidebarConfig(SidebarConfig sidebarConfig)
    {
        HasSidebarConfig.setSidebarConfig(this, sidebarConfig);
    }

    protected void setSidebarConfig(SidebarConfig.SidebarConfigSupplier sidebarConfigSupplier)
    {
        HasSidebarConfig.setSidebarConfig(this, sidebarConfigSupplier);
    }
}
