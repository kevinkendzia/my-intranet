package de.kkendzia.myintranet.ei.ui.views.admin.permission.search;

import de.kkendzia.myintranet.app.service._framework.SimpleSearchFilters;
import de.kkendzia.myintranet.app.service._framework.SimpleSearchItem;
import de.kkendzia.myintranet.app.service.user.PermissionSearchService;
import de.kkendzia.myintranet.ei.core.presenter.Presenter;
import de.kkendzia.myintranet.ei.core.search.DefaultSearchPresenter;

@Presenter
public class AdminPermissionSearchPresenter extends DefaultSearchPresenter<SimpleSearchItem, SimpleSearchFilters>
{
    public AdminPermissionSearchPresenter(PermissionSearchService searchService)
    {
        super(searchService);
    }
}
