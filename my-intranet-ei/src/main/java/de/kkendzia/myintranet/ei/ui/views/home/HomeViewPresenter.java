package de.kkendzia.myintranet.ei.ui.views.home;

import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.spring.annotation.RouteScope;
import de.kkendzia.myintranet.app._framework.cqrs.query.QueryMediator;
import de.kkendzia.myintranet.app.news.queries.FindCurrentNews;
import de.kkendzia.myintranet.app.news.queries.FindCurrentNews.NewsItem;
import de.kkendzia.myintranet.app.notification.queries.FindUnseenNotifications;
import de.kkendzia.myintranet.app.notification.queries.FindUnseenNotifications.NotificationItem;
import de.kkendzia.myintranet.app.useractions._shared.ActionItem;
import de.kkendzia.myintranet.app.useractions.queries.FindFavoriteActions;
import de.kkendzia.myintranet.app.useractions.queries.FindRecentActions;
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
        final var dp = new QueryDataProvider<ActionItem>(quMediator).withConfigurableFilter();
        dp.setFilter(new FindRecentActions(session.userId()));
        return dp;
    }

    public DataProvider<ActionItem, Void> createFavoriteActionDataProvider()
    {
        final var dp = new QueryDataProvider<ActionItem>(quMediator).withConfigurableFilter();
        dp.setFilter(new FindFavoriteActions(session.userId()));
        return dp;
    }

    public DataProvider<NewsItem, Void> createNewsDataProvider()
    {
        final var dp = new QueryDataProvider<NewsItem>(quMediator).withConfigurableFilter();
        dp.setFilter(new FindCurrentNews());
        return dp;
    }

    public DataProvider<NotificationItem, Void> createNotificationsDataProvider()
    {
        final var dp = new QueryDataProvider<NotificationItem>(quMediator).withConfigurableFilter();
        dp.setFilter(new FindUnseenNotifications(session.userId()));
        return dp;
    }
}
