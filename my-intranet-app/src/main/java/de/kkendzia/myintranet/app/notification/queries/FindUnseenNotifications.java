package de.kkendzia.myintranet.app.notification.queries;

import de.kkendzia.myintranet.app._framework.cqrs.query.paged.PagedQuery;
import de.kkendzia.myintranet.app._shared.failures.SearchFailure;
import de.kkendzia.myintranet.domain.user.EIUser.EIUserID;

import java.time.LocalDateTime;

public record FindUnseenNotifications(EIUserID userId)
        implements PagedQuery<FindUnseenNotifications.NotificationItem, SearchFailure>
{
    public interface FindUnseenNotificationsHandler
            extends PagedQueryHandler<FindUnseenNotifications, NotificationItem, SearchFailure>
    {
        @Override
        default Class<FindUnseenNotifications> getQueryClass()
        {
            return FindUnseenNotifications.class;
        }
    }

    public record NotificationItem(
            LocalDateTime timestamp,
            String title,
            String message)
    {
        // just a record
    }
}
