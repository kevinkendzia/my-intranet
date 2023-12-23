package de.kkendzia.myintranet.ei.ui.views.ah.search;

import de.kkendzia.myintranet.app._framework.cqrs.query.QueryMediator;
import de.kkendzia.myintranet.app.search.queries.SearchAhs;
import de.kkendzia.myintranet.ei._framework.presenter.Presenter;
import de.kkendzia.myintranet.ei._framework.view.search.AbstractSearchPresenter;

@Presenter
public class AhSearchPresenter extends AbstractSearchPresenter<SearchAhs.ResultItem>
{
    public AhSearchPresenter(final QueryMediator quMediator)
    {
        super(quMediator);
    }
}
