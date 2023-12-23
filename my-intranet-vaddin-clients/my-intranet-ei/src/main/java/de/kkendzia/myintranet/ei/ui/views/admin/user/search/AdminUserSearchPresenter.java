package de.kkendzia.myintranet.ei.ui.views.admin.user.search;

import de.kkendzia.myintranet.app._framework.cqrs.query.QueryMediator;
import de.kkendzia.myintranet.app.search.queries.SearchEIUsers;
import de.kkendzia.myintranet.ei._framework.presenter.Presenter;
import de.kkendzia.myintranet.ei._framework.view.search.AbstractSearchPresenter;

@Presenter
public class AdminUserSearchPresenter extends AbstractSearchPresenter<SearchEIUsers.ResultItem>
{
    public AdminUserSearchPresenter(QueryMediator quMediator)
    {
        super(quMediator);
    }
}
