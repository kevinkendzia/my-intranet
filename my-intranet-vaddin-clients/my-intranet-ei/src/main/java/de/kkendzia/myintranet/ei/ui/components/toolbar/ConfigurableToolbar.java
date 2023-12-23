package de.kkendzia.myintranet.ei.ui.components.toolbar;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.shared.HasThemeVariant;
import com.vaadin.flow.function.SerializableSupplier;
import de.kkendzia.myintranet.ei.ui.components.text.Badge;

import static java.util.Objects.requireNonNull;

// TODO: sticky? shrinking toolbar?
public class ConfigurableToolbar extends Composite<Toolbar> implements HasThemeVariant<Toolbar.ToolbarVariant>
{
    private SerializableSupplier<ToolbarConfiguration> configSupplier;

    public ConfigurableToolbar(SerializableSupplier<ToolbarConfiguration> configSupplier)
    {
        this.configSupplier = requireNonNull(configSupplier, "configSupplier can't be null!");

        Toolbar root = getContent();
        root.addClassNames("configurable-toolbar");

        rebuild();
    }

    public ConfigurableToolbar(ToolbarConfiguration config)
    {
        this(() -> requireNonNull(config, "config can't be null!"));
    }


    public void rebuild()
    {
        ToolbarConfiguration config = this.configSupplier.get();

        Toolbar root = getContent();
        root.removeAll();

        if (config.preTitle() != null && !config.preTitle().isBlank())
        {
            root.addTitle(new H3(config.preTitle()));
        }
        if (config.title() != null && !config.title().isBlank())
        {
            root.addTitle(new H2(config.title()));
        }
        if (config.subTitle() != null && !config.subTitle().isBlank())
        {
            root.addTitle(new H3(config.subTitle()));
        }

        for (ToolbarConfiguration.ToolbarBadge badge : config.badges())
        {
            // TODO action?
            root.addBadge(new Badge(badge.label()));
        }

        for (ToolbarConfiguration.ToolbarAction action : config.actions())
        {
            root.addAction(new Button(action.label(), e -> action.action().run()));
        }
    }

    public void setConfigSupplier(SerializableSupplier<ToolbarConfiguration> configSupplier)
    {
        this.configSupplier = configSupplier;
    }
}
