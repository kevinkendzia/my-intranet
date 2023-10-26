package de.kkendzia.myintranet.ei.core.view.mixins;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.shared.Registration;

public interface ToolbarNotifier
{
    @SuppressWarnings("UnusedReturnValue")
    default Registration addToolbarChangeListener(ComponentEventListener<ToolbarChangeEvent> listener)
    {
        return ComponentUtil.addListener((Component) this, ToolbarChangeEvent.class, listener);
    }

    default void fireToolbarChange(ToolbarChangeEvent event)
    {
        ComponentUtil.fireEvent((Component) this, event);
    }

    default void fireToolbarChange(boolean fromClient)
    {
        fireToolbarChange(new ToolbarChangeEvent((Component) this, fromClient));
    }
    default void fireToolbarChange()
    {
        fireToolbarChange(false);
    }

    class ToolbarChangeEvent extends ComponentEvent<Component>
    {
        public ToolbarChangeEvent(
                Component source,
                boolean fromClient)
        {
            super(source, fromClient);
        }
    }

    @FunctionalInterface
    interface ToolbarChangeListener extends ComponentEventListener<ToolbarChangeEvent>
    {
        @Override
        default void onComponentEvent(ToolbarChangeEvent event)
        {
            onToolbarChange(event);
        }

        void onToolbarChange(ToolbarChangeEvent event);
    }
}
