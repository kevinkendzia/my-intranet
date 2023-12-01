package de.kkendzia.myintranet.ei.ui.views.home;

import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.spring.annotation.RouteScope;
import de.kkendzia.myintranet.app._framework.cqrs.query.QueryMediator;
import de.kkendzia.myintranet.app.news.queries.FindCurrentNews;
import de.kkendzia.myintranet.app.news.queries.FindCurrentNews.NewsItem;
import de.kkendzia.myintranet.app.notification.queries.FindUnseenNotifications;
import de.kkendzia.myintranet.app.notification.queries.FindUnseenNotifications.NotificationItem;
import de.kkendzia.myintranet.app.useractions.queries.FindFavoriteActions;
import de.kkendzia.myintranet.app.useractions.queries.FindRecentActions;
import de.kkendzia.myintranet.app.useractions.shared.ActionItem;
import de.kkendzia.myintranet.ei._framework.presenter.EIPresenter;
import de.kkendzia.myintranet.ei._framework.presenter.Presenter;
import de.kkendzia.myintranet.ei.core.session.EISession;
import de.kkendzia.myintranet.ei.ui.tools.data.QueryDataProvider;

import static java.util.Objects.requireNonNull;

@Presenter
@RouteScope
public class HomeViewPresenter implements EIPresenter
{
    private final transient QueryMediator quMediator;
    private final EISession session;

    public HomeViewPresenter(final QueryMediator quMediator, EISession session)
    {
        this.quMediator = requireNonNull(quMediator, "quMediator can't be null!");
        this.session = requireNonNull(session, "session can't be null!");
    }

    public DataProvider<ActionItem, Void> createRecentActionDataProvider()
    {
        return new QueryDataProvider<ActionItem>(quMediator)
                .withConvertedFilter(unused -> new FindRecentActions(session.userId()));
    }

    public DataProvider<ActionItem, Void> createFavoriteActionDataProvider()
    {
        return new QueryDataProvider<ActionItem>(quMediator)
                .withConvertedFilter(unused -> new FindFavoriteActions(session.userId()));
    }

    public DataProvider<NewsItem, Void> createNewsDataProvider()
    {
        return new QueryDataProvider<NewsItem>(quMediator)
                .withConvertedFilter(unused -> new FindCurrentNews());
    }

    public DataProvider<NotificationItem, Void> createNotificationsDataProvider()
    {
        return new QueryDataProvider<NotificationItem>(quMediator)
                .withConvertedFilter(unused -> new FindUnseenNotifications(session.userId()));
    }
}
