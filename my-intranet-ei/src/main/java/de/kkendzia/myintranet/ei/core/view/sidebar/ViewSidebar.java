package de.kkendzia.myintranet.ei.core.view.sidebar;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import static java.util.Objects.requireNonNull;

public class ViewSidebar extends Composite<VerticalLayout>
{
    public ViewSidebar(SidebarConfig config)
    {
        requireNonNull(config, "config can't be null!");

        VerticalLayout root = getContent();
        root.addClassNames("ei-view-sidebar");
        root.setSizeUndefined();
        root.setAlignItems(FlexComponent.Alignment.STRETCH);

        for (SidebarConfig.SidebarConfigEntry entry : config.entries())
        {
            root.add(new Button(entry.label(), e -> entry.action().run()));
        }
    }
}
