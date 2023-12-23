package de.kkendzia.myintranet.ei.ui.views.admin.permission.search;

import de.kkendzia.myintranet.app._framework.cqrs.query.QueryMediator;
import de.kkendzia.myintranet.app.search.queries.SearchPermissions;
import de.kkendzia.myintranet.ei._framework.presenter.Presenter;
import de.kkendzia.myintranet.ei._framework.view.search.AbstractSearchPresenter;

@Presenter
public class AdminPermissionSearchPresenter extends AbstractSearchPresenter<SearchPermissions.ResultItem>
{
    public AdminPermissionSearchPresenter(QueryMediator quMediator)
    {
        super(quMediator);
    }
}
