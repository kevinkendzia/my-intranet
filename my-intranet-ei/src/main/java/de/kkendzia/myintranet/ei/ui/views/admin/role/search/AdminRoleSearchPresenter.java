package de.kkendzia.myintranet.ei.ui.views.admin.role.search;

import de.kkendzia.myintranet.app._framework.cqrs.QueryMediator;
import de.kkendzia.myintranet.app.search.queries.SearchRoles;
import de.kkendzia.myintranet.ei.core.presenter.Presenter;
import de.kkendzia.myintranet.ei.core.view.search.AbstractSearchPresenter;

@Presenter
public class AdminRoleSearchPresenter extends AbstractSearchPresenter<SearchRoles.ResultItem>
{
    public AdminRoleSearchPresenter(QueryMediator quMediator)
    {
        super(quMediator);
    }
}
