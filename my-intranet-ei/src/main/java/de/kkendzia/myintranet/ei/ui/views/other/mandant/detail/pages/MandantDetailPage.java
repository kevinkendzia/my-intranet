package de.kkendzia.myintranet.ei.ui.views.other.mandant.detail.pages;

import com.vaadin.flow.component.HasElement;
import de.kkendzia.myintranet.ei.core.view.toolbar.HasToolbarConfig;

public interface MandantDetailPage extends HasElement, HasToolbarConfig
{
    void refresh();
}
