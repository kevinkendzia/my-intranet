package de.kkendzia.myintranet.ei.ui.views.mandant.search;

import de.kkendzia.myintranet.app.search.mandant.MandantSearchService;
import de.kkendzia.myintranet.app.search.mandant.MandantSearchService.MandantSearchFilters;
import de.kkendzia.myintranet.app.search.mandant.MandantSearchService.MandantSearchItem;
import de.kkendzia.myintranet.ei.core.presenter.Presenter;
import de.kkendzia.myintranet.ei.core.view.search.DefaultSearchPresenter;

@Presenter
public class MandantSearchPresenter extends DefaultSearchPresenter<MandantSearchItem, MandantSearchFilters>
{
    public MandantSearchPresenter(MandantSearchService searchService)
    {
        super(searchService);
    }
}
