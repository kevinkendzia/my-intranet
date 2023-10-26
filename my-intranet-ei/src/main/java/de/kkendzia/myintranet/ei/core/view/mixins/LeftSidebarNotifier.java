package de.kkendzia.myintranet.ei.core.view.mixins;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.shared.Registration;
import de.kkendzia.myintranet.ei.ui.components.sidebar.SidebarConfiguration;

public interface LeftSidebarNotifier
{
    @SuppressWarnings("UnusedReturnValue")
    default Registration addLeftSidebarChangeListener(ComponentEventListener<SidebarChangeEvent> listener)
    {
        return ComponentUtil.addListener((Component) this, SidebarChangeEvent.class, listener);
    }

    default void fireLeftSidebarChange(SidebarChangeEvent event)
    {
        ComponentUtil.fireEvent((Component) this, event);
    }

    default void fireLeftSidebarChange(boolean fromClient, SidebarConfiguration config)
    {
        fireLeftSidebarChange(new SidebarChangeEvent((Component) this, fromClient));
    }

    default void fireLeftSidebarChange()
    {
        fireLeftSidebarChange(false, null);
    }

    class SidebarChangeEvent extends ComponentEvent<Component>
    {
        public SidebarChangeEvent(
                Component source,
                boolean fromClient)
        {
            super(source, fromClient);
        }
    }
}
