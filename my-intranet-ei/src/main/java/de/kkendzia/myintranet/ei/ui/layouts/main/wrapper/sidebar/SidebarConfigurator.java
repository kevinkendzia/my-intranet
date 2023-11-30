package de.kkendzia.myintranet.ei.ui.layouts.main.wrapper.sidebar;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.function.SerializableSupplier;
import de.kkendzia.components.expandablesidebar.ExpandableSidebar;
import de.kkendzia.components.expandablesidebar.ExpandableSidebarHeader;
import de.kkendzia.components.expandablesidebar.ExpandableSidebarToggle;

import static java.util.Objects.requireNonNull;

public class SidebarConfigurator extends Composite<ExpandableSidebar>
{
    private SerializableSupplier<SidebarConfiguration> configSupplier;
    private ComponentFactory<SidebarConfiguration.SidebarAction> actionFactory = new DefaultActionFactory();
    private ComponentFactory<SidebarConfiguration.SidebarText> textFactory = new DefaultTextFactory();
    private ComponentFactory<SidebarConfiguration.SidebarHeader> headerFactory = new DefaultHeaderFactory(actionFactory);

    public SidebarConfigurator(SerializableSupplier<SidebarConfiguration> configSupplier)
    {
        this.configSupplier = requireNonNull(configSupplier, "configSupplier can't be null!");

        ExpandableSidebar root = getContent();
        root.addClassNames("configurable-sidebar");
        root.setWidth("4em");
        root.setWidthExpanded("17em");

        rebuild();
    }

    public SidebarConfigurator(SidebarConfiguration config)
    {
        this(() -> requireNonNull(config, "config can't be null!"));
    }

    public void rebuild()
    {
        SidebarConfiguration config = this.configSupplier.get();

        ExpandableSidebar root = getContent();

        if (config.header() != null)
        {
            root.add(headerFactory.create(config.header()));
        }

        for (SidebarConfiguration.SidebarContent entry : config.content())
        {
            if (entry instanceof SidebarConfiguration.SidebarAction action)
            {
                root.add(actionFactory.create(action));
            }
            else if (entry instanceof SidebarConfiguration.SidebarText text)
            {
                root.add(textFactory.create(text));
            }
        }
    }

    //region SETTER
    public void setConfigSupplier(SerializableSupplier<SidebarConfiguration> configSupplier)
    {
        this.configSupplier = configSupplier;
    }

    public void setHeaderFactory(ComponentFactory<SidebarConfiguration.SidebarHeader> headerFactory)
    {
        this.headerFactory = requireNonNull(headerFactory, "headerFactory can't be null!");
    }

    public void setActionFactory(ComponentFactory<SidebarConfiguration.SidebarAction> actionFactory)
    {
        this.actionFactory = requireNonNull(actionFactory, "actionFactory can't be null!");
    }

    public void setTextFactory(ComponentFactory<SidebarConfiguration.SidebarText> textFactory)
    {
        this.textFactory = requireNonNull(textFactory, "textFactory can't be null!");
    }
    //endregion

    //region TYPES
    public static class DefaultHeaderFactory implements ComponentFactory<SidebarConfiguration.SidebarHeader>
    {
        private ComponentFactory<SidebarConfiguration.SidebarAction> actionFactory;

        public DefaultHeaderFactory(ComponentFactory<SidebarConfiguration.SidebarAction> actionFactory)
        {
            this.actionFactory = requireNonNull(actionFactory, "actionFactory can't be null!");
        }

        @Override
        public Component create(SidebarConfiguration.SidebarHeader header)
        {
            ExpandableSidebarHeader headerComponent = new ExpandableSidebarHeader("", "");
            headerComponent.setSuffixComponent(
                    new ExpandableSidebarToggle(
                            VaadinIcon.CHEVRON_RIGHT.create(),
                            VaadinIcon.CHEVRON_LEFT.create()));

//            headerComponent.addClassName(Display.FLEX);
//            headerComponent.addClassName(FlexDirection.COLUMN);
//            headerComponent.addClassName(AlignItems.STRETCH);

            if (header.title() != null && !header.title().isEmpty())
            {
                headerComponent.setHeaderText(header.title());
            }

//            if (header.description() != null && !header.description().isEmpty())
//            {
//                headerComponent.setSubHeaderText(header.description());
//            }

            if (header.description() != null && !header.description().isEmpty())
            {
                headerComponent.add(new Paragraph(header.description()));
            }

            if (header.actions() != null && !header.actions().isEmpty())
            {
                for (SidebarConfiguration.SidebarAction action : header.actions())
                {
                    headerComponent.add(actionFactory.create(action));
                }
            }
            return headerComponent;
        }

        public void setActionFactory(ComponentFactory<SidebarConfiguration.SidebarAction> actionFactory)
        {
            this.actionFactory = requireNonNull(actionFactory, "actionFactory can't be null!");
        }
    }

    public static class DefaultActionFactory implements ComponentFactory<SidebarConfiguration.SidebarAction>
    {
        @Override
        public Component create(SidebarConfiguration.SidebarAction action)
        {
            return new Button(action.label(), e -> action.action().run());
        }
    }

    public static class DefaultTextFactory implements ComponentFactory<SidebarConfiguration.SidebarText>
    {
        @Override
        public Component create(SidebarConfiguration.SidebarText text)
        {
            return new Paragraph(text.text());
        }
    }
    //endregion
}
