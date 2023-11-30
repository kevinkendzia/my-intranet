package de.kkendzia.myintranet.ei.ui.layouts.main.sidebar;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.function.SerializableSupplier;
import de.kkendzia.components.expandablesidebar.ExpandableSidebar;
import de.kkendzia.components.expandablesidebar.ExpandableSidebarHeader;
import de.kkendzia.components.expandablesidebar.ExpandableSidebarToggle;
import de.kkendzia.myintranet.ei.ui.layouts.main.sidebar.SidebarConfiguration.SidebarAction;
import de.kkendzia.myintranet.ei.ui.layouts.main.sidebar.SidebarConfiguration.SidebarHeader;
import de.kkendzia.myintranet.ei.ui.layouts.main.sidebar.SidebarConfiguration.SidebarText;

import static java.util.Objects.requireNonNull;

public class SidebarConfigurator extends Composite<ExpandableSidebar>
{
    private SerializableSupplier<SidebarConfiguration> configSupplier;
    private ComponentFactory<SidebarAction> actionFactory = new DefaultActionFactory();
    private ComponentFactory<SidebarText> textFactory = new DefaultTextFactory();
    private ComponentFactory<SidebarHeader> headerFactory = new DefaultHeaderFactory(actionFactory);

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
            if (entry instanceof SidebarAction action)
            {
                root.add(actionFactory.create(action));
            }
            else if (entry instanceof SidebarText text)
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
