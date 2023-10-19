package de.kkendzia.myintranet.ei.core.view.toolbar;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.function.SerializableSupplier;

import static java.util.Objects.requireNonNull;

// TODO: sticky? shrinking toolbar?
public class ViewToolbar extends Composite<HorizontalLayout> implements ToolbarNotifier.ToolbarChangeListener
{
    private SerializableSupplier<ToolbarConfig> configSupplier;

    public ViewToolbar(SerializableSupplier<ToolbarConfig> configSupplier)
    {
        this.configSupplier = requireNonNull(configSupplier, "configSupplier can't be null!");

        HorizontalLayout root = getContent();
        root.addClassNames("view-toolbar");
        root.setAlignItems(FlexComponent.Alignment.STRETCH);
        root.setJustifyContentMode(JustifyContentMode.BETWEEN);

        rebuild();
    }

    public ViewToolbar(ToolbarConfig config)
    {
        this(() -> requireNonNull(config, "config can't be null!"));
    }


    private void rebuild()
    {
        ToolbarConfig config = this.configSupplier.get();

        HorizontalLayout root = getContent();
        root.removeAll();

        if (config.title() != null && !config.title().isBlank())
        {
            root.add(new H2(config.title()));
        }

        HorizontalLayout hlActions = null;
        for (ToolbarConfig.ToolbarAction action : config.actions())
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
    public void onToolbarChange(ToolbarNotifier.ToolbarChangeEvent event)
    {
        event.getOptionalConfig().ifPresent(c -> setConfigSupplier(() -> c));
        rebuild();
    }

    private void setConfigSupplier(SerializableSupplier<ToolbarConfig> configSupplier)
    {
        this.configSupplier = configSupplier;
    }
}
