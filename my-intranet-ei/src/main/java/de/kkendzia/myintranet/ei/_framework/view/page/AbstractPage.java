package de.kkendzia.myintranet.ei._framework.view.page;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import de.kkendzia.myintranet.ei._framework.view.mixins.HasToolbarConfig;
import de.kkendzia.myintranet.ei._framework.view.mixins.HasLeftSidebar;
import de.kkendzia.myintranet.ei._framework.view.mixins.LeftSidebarNotifier;
import de.kkendzia.myintranet.ei._framework.view.mixins.ToolbarNotifier;
import de.kkendzia.myintranet.ei.ui.components.toolbar.ToolbarConfiguration;
import de.kkendzia.myintranet.ei.ui.layouts.main.sidebar.SidebarConfiguration;

public abstract class AbstractPage<C extends Component> extends Composite<C>
        implements HasToolbarConfig, ToolbarNotifier, HasLeftSidebar, LeftSidebarNotifier, EIPage
{
    protected void setToolbarConfig(ToolbarConfiguration toolbarConfiguration)
    {
        HasToolbarConfig.setToolbarConfig(this, toolbarConfiguration);
    }

    protected void setToolbarConfig(ToolbarConfiguration.ToolbarConfigSupplier toolbarConfigSupplier)
    {
        HasToolbarConfig.setToolbarConfig(this, toolbarConfigSupplier);
    }

    protected void setSidebarConfig(SidebarConfiguration sidebarConfig)
    {
        HasLeftSidebar.setLeftSidebarConfig(this, sidebarConfig);
    }

    protected void setSidebarConfig(LeftSidebarConfigSupplier sidebarConfigSupplier)
    {
        HasLeftSidebar.setLeftSidebarConfig(this, sidebarConfigSupplier);
    }
}
