package de.kkendzia.myintranet.ei.ui.views.admin.role.search;

import de.kkendzia.myintranet.app.service._framework.SimpleSearchFilters;
import de.kkendzia.myintranet.app.service._framework.SimpleSearchItem;
import de.kkendzia.myintranet.app.service.user.RoleSearchService;
import de.kkendzia.myintranet.ei.core.presenter.Presenter;
import de.kkendzia.myintranet.ei.core.view.search.DefaultSearchPresenter;

@Presenter
public class AdminRoleSearchPresenter extends DefaultSearchPresenter<SimpleSearchItem, SimpleSearchFilters>
{
    public AdminRoleSearchPresenter(RoleSearchService searchService)
    {
        super(searchService);
    }
}