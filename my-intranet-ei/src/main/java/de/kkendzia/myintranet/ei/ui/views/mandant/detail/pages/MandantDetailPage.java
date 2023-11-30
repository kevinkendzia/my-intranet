package de.kkendzia.myintranet.ei.ui.views.mandant.detail.pages;

import de.kkendzia.myintranet.ei._framework.view.page.EIPage;
import de.kkendzia.myintranet.ei._framework.view.mixins.HasToolbarConfig;

public interface MandantDetailPage extends EIPage, HasToolbarConfig
{
    void refresh();
}
