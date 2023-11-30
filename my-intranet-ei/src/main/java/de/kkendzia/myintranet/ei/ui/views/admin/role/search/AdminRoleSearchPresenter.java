package de.kkendzia.myintranet.ei.ui.views.admin.role.search;

import de.kkendzia.myintranet.app._framework.cqrs.query.QueryMediator;
import de.kkendzia.myintranet.app.search.queries.SearchRoles;
import de.kkendzia.myintranet.ei._framework.presenter.Presenter;
import de.kkendzia.myintranet.ei._framework.view.search.AbstractSearchPresenter;

@Presenter
public class AdminRoleSearchPresenter extends AbstractSearchPresenter<SearchRoles.ResultItem>
{
    public AdminRoleSearchPresenter(QueryMediator quMediator)
    {
        super(quMediator);
    }
}
