package de.kkendzia.myintranet.ei.core.view;

import com.vaadin.flow.component.HasElement;
import de.kkendzia.myintranet.ei.core.view.toolbar.ToolbarConfig;

import java.util.Optional;

public interface EIView extends HasElement
{
    Optional<ToolbarConfig> getOptionalToolbarConfig();
}
