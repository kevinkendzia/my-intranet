package de.kkendzia.myintranet.ei.core.view.sidebar;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.function.SerializableSupplier;

import static java.util.Objects.requireNonNull;

public class ViewSidebar extends Composite<VerticalLayout> implements SidebarNotifier.SidebarChangeListener
{
    private SerializableSupplier<SidebarConfig> configSupplier;

    public ViewSidebar(SerializableSupplier<SidebarConfig> configSupplier)
    {
        this.configSupplier=requireNonNull(configSupplier, "configSupplier can't be null!");

        VerticalLayout root = getContent();
        root.addClassNames("ei-view-sidebar");
        root.setSizeUndefined();
        root.setAlignItems(FlexComponent.Alignment.STRETCH);

        rebuild();
    }

    public ViewSidebar(SidebarConfig config)
    {
        this(() -> requireNonNull(config, "config can't be null!"));
    }

    private void rebuild()
    {
        SidebarConfig config = this.configSupplier.get();
        VerticalLayout root = getContent();
        root.removeAll();
        for (SidebarConfig.SidebarConfigEntry entry : config.entries())
        {
            root.add(new Button(entry.label(), e -> entry.action().run()));
        }
    }

    private void setConfigSupplier(SerializableSupplier<SidebarConfig> configSupplier)
    {
        this.configSupplier = configSupplier;
    }

    @Override
    public void onSidebarChange(SidebarNotifier.SidebarChangeEvent event)
    {
        event.getOptionalConfig().ifPresent(c -> setConfigSupplier(() -> c));
        rebuild();
    }
}
