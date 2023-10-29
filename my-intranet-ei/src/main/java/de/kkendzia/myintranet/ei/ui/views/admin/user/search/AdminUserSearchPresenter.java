package de.kkendzia.myintranet.ei.ui.views.admin.user.search;

import de.kkendzia.myintranet.app.service.search.user.EIUserSearchService;
import de.kkendzia.myintranet.app.service.search.user.EIUserSearchService.EIUserSearchFilters;
import de.kkendzia.myintranet.app.service.search.user.EIUserSearchService.EIUserSearchItem;
import de.kkendzia.myintranet.ei.core.presenter.Presenter;
import de.kkendzia.myintranet.ei.core.view.search.DefaultSearchPresenter;

@Presenter
public class AdminUserSearchPresenter extends DefaultSearchPresenter<EIUserSearchItem, EIUserSearchFilters>
{
    public AdminUserSearchPresenter(EIUserSearchService searchService)
    {
        super(searchService);
    }
}
