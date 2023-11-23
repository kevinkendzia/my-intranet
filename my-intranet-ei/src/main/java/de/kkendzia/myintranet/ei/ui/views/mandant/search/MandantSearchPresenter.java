package de.kkendzia.myintranet.ei.ui.views.mandant.search;

import de.kkendzia.myintranet.app._framework.cqrs.query.QueryMediator;
import de.kkendzia.myintranet.app.search.queries.SearchMandanten;
import de.kkendzia.myintranet.ei.core.presenter.Presenter;
import de.kkendzia.myintranet.ei.core.view.search.AbstractSearchPresenter;

@Presenter
public class MandantSearchPresenter extends AbstractSearchPresenter<SearchMandanten.ResultItem>
{
    public MandantSearchPresenter(QueryMediator quMediator)
    {
        super(quMediator);
    }
}
