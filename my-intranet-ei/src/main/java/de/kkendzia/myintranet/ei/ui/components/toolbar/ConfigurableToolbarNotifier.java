package de.kkendzia.myintranet.ei.ui.components.toolbar;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.shared.Registration;

import java.util.Optional;

public interface ConfigurableToolbarNotifier
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

    default void fireToolbarChange(boolean fromClient, ToolbarConfiguration config)
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
        private final ToolbarConfiguration config;

        public ToolbarChangeEvent(
                Component source,
                boolean fromClient,
                ToolbarConfiguration config)
        {
            super(source, fromClient);
            this.config = config;
        }

        public ToolbarConfiguration getConfig()
        {
            return config;
        }
        public Optional<ToolbarConfiguration> getOptionalConfig()
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
