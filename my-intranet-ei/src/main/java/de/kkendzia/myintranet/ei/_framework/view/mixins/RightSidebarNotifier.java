package de.kkendzia.myintranet.ei._framework.view.mixins;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.shared.Registration;
import de.kkendzia.myintranet.ei.ui.layouts.main.wrapper.sidebar.SidebarConfiguration;

public interface RightSidebarNotifier
{
    @SuppressWarnings("UnusedReturnValue")
    default Registration addRightSidebarChangeListener(ComponentEventListener<RightSidebarChangeEvent> listener)
    {
        return ComponentUtil.addListener((Component) this, RightSidebarChangeEvent.class, listener);
    }

    default void fireRightSidebarChange(RightSidebarChangeEvent event)
    {
        ComponentUtil.fireEvent((Component) this, event);
    }

    default void fireRightSidebarChange(boolean fromClient, SidebarConfiguration config)
    {
        fireRightSidebarChange(new RightSidebarChangeEvent((Component) this, fromClient));
    }

    default void fireRightSidebarChange()
    {
        fireRightSidebarChange(false, null);
    }

    class RightSidebarChangeEvent extends ComponentEvent<Component>
    {
        public RightSidebarChangeEvent(
                Component source,
                boolean fromClient)
        {
            super(source, fromClient);
        }
    }
}
