package de.kkendzia.myintranet.ei.core.view.toolbar;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.shared.Registration;

import java.util.Optional;

public interface ToolbarNotifier
{
    default Registration addToolbarChangeListener(ComponentEventListener<ToolbarChangeEvent> listener)
    {
        return ComponentUtil.addListener((Component) this, ToolbarChangeEvent.class, listener);
    }

    default void fireToolbarChange(ToolbarChangeEvent event)
    {
        ComponentUtil.fireEvent((Component) this, event);
    }

    default void fireToolbarChange(boolean fromClient, ToolbarConfig config)
    {
        fireToolbarChange(new ToolbarChangeEvent((Component) this, fromClient, config));
    }
    default void fireToolbarChange(boolean fromClient)
    {
        fireToolbarChange(fromClient, null);
    }
    default void fireToolbarChange()
    {
        fireToolbarChange(false, null);
    }

    class ToolbarChangeEvent extends ComponentEvent<Component>
    {
        private final ToolbarConfig config;

        public ToolbarChangeEvent(
                Component source,
                boolean fromClient,
                ToolbarConfig config)
        {
            super(source, fromClient);
            this.config = config;
        }

        public ToolbarConfig getConfig()
        {
            return config;
        }
        public Optional<ToolbarConfig> getOptionalConfig()
        {
            return Optional.ofNullable(config);
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
