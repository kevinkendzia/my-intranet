package de.kkendzia.myintranet.ei.ui.components.toolbar;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.function.SerializableSupplier;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;

import static java.util.Objects.requireNonNull;

// TODO: sticky? shrinking toolbar?
public class ConfigurableToolbar extends Composite<HorizontalLayout> implements ConfigurableToolbarNotifier.ToolbarChangeListener
{
    private SerializableSupplier<ToolbarConfiguration> configSupplier;

    public ConfigurableToolbar(SerializableSupplier<ToolbarConfiguration> configSupplier)
    {
        this.configSupplier = requireNonNull(configSupplier, "configSupplier can't be null!");

        HorizontalLayout root = getContent();
        root.addClassNames("view-toolbar");
        root.addClassNames(Padding.Horizontal.MEDIUM);
        root.setAlignItems(Alignment.STRETCH);
        root.setJustifyContentMode(JustifyContentMode.BETWEEN);

        rebuild();
    }

    public ConfigurableToolbar(ToolbarConfiguration config)
    {
        this(() -> requireNonNull(config, "config can't be null!"));
    }


    private void rebuild()
    {
        ToolbarConfiguration config = this.configSupplier.get();

        HorizontalLayout root = getContent();
        root.removeAll();

        if (config.title() != null && !config.title().isBlank())
        {
            root.add(new H2(config.title()));
        }

        HorizontalLayout hlActions = null;
        for (ToolbarConfiguration.ToolbarAction action : config.actions())
        {
            if (hlActions == null)
            {
                hlActions = new HorizontalLayout();
                hlActions.setPadding(false);
                hlActions.setJustifyContentMode(JustifyContentMode.END);
                root.addAndExpand(hlActions);
            }
            hlActions.add(new Button(action.label(), e -> action.action().run()));
        }
    }

    @Override
    public void onToolbarChange(ConfigurableToolbarNotifier.ToolbarChangeEvent event)
    {
        event.getOptionalConfig().ifPresent(c -> setConfigSupplier(() -> c));
        rebuild();
    }

    private void setConfigSupplier(SerializableSupplier<ToolbarConfiguration> configSupplier)
    {
        this.configSupplier = configSupplier;
    }
}
