package de.kkendzia.myintranet.ei.ui.views.mandant.detail.pages;

import de.kkendzia.myintranet.ei.core.view.page.EIPage;
import de.kkendzia.myintranet.ei.core.view.mixins.HasToolbarConfig;

public interface MandantDetailPage extends EIPage, HasToolbarConfig
{
    void refresh();
}
