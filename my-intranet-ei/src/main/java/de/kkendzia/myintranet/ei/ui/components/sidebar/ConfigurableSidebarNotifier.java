package de.kkendzia.myintranet.ei.ui.components.sidebar;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.shared.Registration;

import java.util.Optional;

public interface ConfigurableSidebarNotifier
{
    @SuppressWarnings("UnusedReturnValue")
    default Registration addSidebarChangeListener(ComponentEventListener<SidebarChangeEvent> listener)
    {
        return ComponentUtil.addListener((Component) this, SidebarChangeEvent.class, listener);
    }

    default void fireSidebarChange(SidebarChangeEvent event)
    {
        ComponentUtil.fireEvent((Component) this, event);
    }

    default void fireSidebarChange(boolean fromClient, SidebarConfig config)
    {
        fireSidebarChange(new SidebarChangeEvent((Component) this, fromClient, config));
    }
    default void fireSidebarChange()
    {
        fireSidebarChange(false, null);
    }

    class SidebarChangeEvent extends ComponentEvent<Component>
    {
        private SidebarConfig config;

        public SidebarChangeEvent(
                Component source,
                boolean fromClient,
                SidebarConfig config)
        {
            super(source, fromClient);
            this.config = config;
        }

        public SidebarConfig getConfig()
        {
            return config;
        }
        public Optional<SidebarConfig> getOptionalConfig()
        {
            return Optional.ofNullable(config);
        }
    }

    @FunctionalInterface
    interface SidebarChangeListener extends ComponentEventListener<SidebarChangeEvent>
    {
        @Override
        default void onComponentEvent(SidebarChangeEvent event)
        {
            onSidebarChange(event);
        }

        void onSidebarChange(SidebarChangeEvent event);
    }
}
