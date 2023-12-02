package de.kkendzia.myintranet.microstream.queries.notifications;

import de.kkendzia.myintranet.app._framework.cqrs.query.paged.Paging;
import de.kkendzia.myintranet.app._framework.result.ListResult;
import de.kkendzia.myintranet.app._shared.failures.SearchFailure;
import de.kkendzia.myintranet.app.notification.queries.FindUnseenNotifications;
import de.kkendzia.myintranet.app.notification.queries.FindUnseenNotifications.FindUnseenNotificationsHandler;
import de.kkendzia.myintranet.app.notification.queries.FindUnseenNotifications.NotificationItem;
import de.kkendzia.myintranet.domain.notification.UserNotification;
import de.kkendzia.myintranet.microstream._framework.AbstractMSQueryHandler;
import org.eclipse.store.storage.types.StorageManager;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class MSFindUnseenNotificationsHandler
        extends AbstractMSQueryHandler<UserNotification>
        implements FindUnseenNotificationsHandler
{
    public MSFindUnseenNotificationsHandler(final StorageManager storageManager)
    {
        super(storageManager);
    }

    @Override
    public ListResult<NotificationItem, SearchFailure> fetchAll(
            final FindUnseenNotifications query,
            final Paging paging)
    {
        final var notifications = getRoot()
                .getNotifications()
                .values()
                .stream()
                .filter(u -> Objects.equals(u.getUser().id(), query.userId()));

        return ListResult.success(
                applyPaging(notifications, paging)
                        .map(x -> new NotificationItem(x.getTimestamp(), x.getTitle(), x.getDescription()))
                        .toList());
    }
}
