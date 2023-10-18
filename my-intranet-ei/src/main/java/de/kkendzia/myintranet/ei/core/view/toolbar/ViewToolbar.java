package de.kkendzia.myintranet.ei.core.view.toolbar;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import static java.util.Objects.requireNonNull;

public class ViewToolbar extends Composite<HorizontalLayout>
{
    public ViewToolbar(ToolbarConfig config)
    {
        requireNonNull(config, "config can't be null!");

        HorizontalLayout root = getContent();
        root.addClassNames("view-toolbar");
        root.setAlignItems(FlexComponent.Alignment.STRETCH);
        root.setJustifyContentMode(JustifyContentMode.BETWEEN);

        if(config.title() != null && !config.title().isBlank())
        {
            root.add(new H2(config.title()));
        }

        HorizontalLayout hlActions = null;
        for (ToolbarConfig.ToolbarAction action : config.actions())
        {
            if(hlActions == null)
            {
                hlActions = new HorizontalLayout();
                hlActions.setPadding(false);
                hlActions.setJustifyContentMode(JustifyContentMode.END);
                root.addAndExpand(hlActions);
            }
            hlActions.add(new Button(action.label(), e -> action.action().run()));
        }
    }
}
