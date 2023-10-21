package de.kkendzia.myintranet.ei.core.view.page;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import de.kkendzia.myintranet.ei.core.view.sidebar.HasSidebarConfig;
import de.kkendzia.myintranet.ei.core.view.sidebar.SidebarConfig;
import de.kkendzia.myintranet.ei.core.view.sidebar.SidebarNotifier;
import de.kkendzia.myintranet.ei.core.view.toolbar.HasToolbarConfig;
import de.kkendzia.myintranet.ei.core.view.toolbar.ToolbarConfig;
import de.kkendzia.myintranet.ei.core.view.toolbar.ToolbarNotifier;

public abstract class AbstractPage<C extends Component> extends Composite<C>
        implements HasToolbarConfig, ToolbarNotifier, HasSidebarConfig, SidebarNotifier, EIPage
{
    protected void setToolbarConfig(ToolbarConfig toolbarConfig)
    {
        HasToolbarConfig.setToolbarConfig(this, toolbarConfig);
    }

    protected void setToolbarConfig(ToolbarConfig.ToolbarConfigSupplier toolbarConfigSupplier)
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
