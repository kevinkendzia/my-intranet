package de.kkendzia.myintranet.ei.core.view.toolbar;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;

import static java.util.Objects.requireNonNull;

public class ViewToolbar extends Composite<HorizontalLayout>
{
    public ViewToolbar(ToolbarConfig config)
    {
        requireNonNull(config, "config can't be null!");

        HorizontalLayout root = getContent();
        root.addClassNames("ei-view-toolbar");
        root.addClassNames(LumoUtility.Padding.Horizontal.MEDIUM);
        root.setAlignItems(FlexComponent.Alignment.STRETCH);
        root.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

        if(config.title() != null && !config.title().isBlank())
        {
            root.add(new H2(config.title()));
        }

        for (ToolbarConfig.ToolbarConfigEntry entry : config.entries())
        {
            root.add(new Button(entry.label(), e -> entry.action().run()));
        }
    }
}
