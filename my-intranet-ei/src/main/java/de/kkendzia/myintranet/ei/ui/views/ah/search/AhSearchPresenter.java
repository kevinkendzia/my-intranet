package de.kkendzia.myintranet.ei.ui.views.ah.search;

import de.kkendzia.myintranet.app._framework.cqrs.QueryMediator;
import de.kkendzia.myintranet.app.search.queries.SearchAhs;
import de.kkendzia.myintranet.ei.core.presenter.Presenter;
import de.kkendzia.myintranet.ei.core.view.search.AbstractSearchPresenter;

@Presenter
public class AhSearchPresenter extends AbstractSearchPresenter<SearchAhs.ResultItem>
{
    public AhSearchPresenter(final QueryMediator quMediator)
    {
        super(quMediator);
    }
}
