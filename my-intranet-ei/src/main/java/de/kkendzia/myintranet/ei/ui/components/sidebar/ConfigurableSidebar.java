package de.kkendzia.myintranet.ei.ui.components.sidebar;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.shared.HasThemeVariant;
import com.vaadin.flow.function.SerializableSupplier;
import com.vaadin.flow.theme.lumo.LumoUtility.AlignItems;
import com.vaadin.flow.theme.lumo.LumoUtility.Display;
import com.vaadin.flow.theme.lumo.LumoUtility.FlexDirection;
import de.kkendzia.myintranet.ei.core.components.ComponentFactory;
import de.kkendzia.myintranet.ei.ui.components.sidebar.SidebarConfiguration.SidebarAction;
import de.kkendzia.myintranet.ei.ui.components.sidebar.SidebarConfiguration.SidebarHeader;
import de.kkendzia.myintranet.ei.ui.components.sidebar.SidebarConfiguration.SidebarText;

import static java.util.Objects.requireNonNull;

public class ConfigurableSidebar extends Composite<Sidebar> implements HasThemeVariant<Sidebar.SidebarVariant>
{
    private SerializableSupplier<SidebarConfiguration> configSupplier;
    private ComponentFactory<SidebarAction> actionFactory = new DefaultActionFactory();
    private ComponentFactory<SidebarText> textFactory = new DefaultTextFactory();
    private ComponentFactory<SidebarHeader> headerFactory = new DefaultHeaderFactory(actionFactory);

    public ConfigurableSidebar(SerializableSupplier<SidebarConfiguration> configSupplier)
    {
        this.configSupplier = requireNonNull(configSupplier, "configSupplier can't be null!");

        Sidebar root = getContent();
        root.addClassNames("configurable-sidebar");

        rebuild();
    }

    public ConfigurableSidebar(SidebarConfiguration config)
    {
        this(() -> requireNonNull(config, "config can't be null!"));
    }

    public void rebuild()
    {
        SidebarConfiguration config = this.configSupplier.get();

        Sidebar root = getContent();
        root.removeAll();

        if(config.header() != null)
        {
            root.add(headerFactory.create(config.header()));
        }

        for (SidebarConfiguration.SidebarContent entry : config.content())
        {
            if(entry instanceof SidebarAction action)
            {
                root.add(actionFactory.create(action));
            }
            else if(entry instanceof SidebarText text)
            {
                root.add(textFactory.create(text));
            }
        }
    }

    public void setConfigSupplier(SerializableSupplier<SidebarConfiguration> configSupplier)
    {
        this.configSupplier = configSupplier;
    }

    //region SETTER
    public void setHeaderFactory(ComponentFactory<SidebarHeader> headerFactory)
    {
        this.headerFactory = requireNonNull(headerFactory, "headerFactory can't be null!");
    }
    public void setActionFactory(ComponentFactory<SidebarAction> actionFactory)
    {
        this.actionFactory = requireNonNull(actionFactory, "actionFactory can't be null!");
    }
    public void setTextFactory(ComponentFactory<SidebarText> textFactory)
    {
        this.textFactory = requireNonNull(textFactory, "textFactory can't be null!");
    }
    //endregion

    //region TYPES
    public static class DefaultHeaderFactory implements ComponentFactory<SidebarHeader>
    {
        private ComponentFactory<SidebarAction> actionFactory;

        public DefaultHeaderFactory(ComponentFactory<SidebarAction> actionFactory)
        {
            this.actionFactory = requireNonNull(actionFactory, "actionFactory can't be null!");
        }

        @Override
        public Component create(SidebarHeader header)
        {
            Header headerComponent = new Header();
            headerComponent.addClassName(Display.FLEX);
            headerComponent.addClassName(FlexDirection.COLUMN);
            headerComponent.addClassName(AlignItems.STRETCH);

            if(header.title() != null && !header.title().isEmpty())
            {
                headerComponent.add(new H2(header.title()));
            }

            if(header.description() != null && !header.description().isEmpty())
            {
                headerComponent.add(new Paragraph(header.description()));
            }

            if(header.actions() != null && !header.actions().isEmpty())
            {
                for (SidebarAction action : header.actions())
                {
                    headerComponent.add(actionFactory.create(action));
                }
            }
            return headerComponent;
        }

        public void setActionFactory(ComponentFactory<SidebarAction> actionFactory)
        {
            this.actionFactory = requireNonNull(actionFactory, "actionFactory can't be null!");
        }
    }

    public static class DefaultActionFactory implements ComponentFactory<SidebarAction>
    {
        @Override
        public Component create(SidebarAction action)
        {
            return new Button(action.label(), e -> action.action().run());
        }
    }
    public static class DefaultTextFactory implements ComponentFactory<SidebarText>
    {
        @Override
        public Component create(SidebarText text)
        {
            return new Paragraph(text.text());
        }
    }
    //endregion
}
