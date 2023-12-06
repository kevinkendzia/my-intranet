package de.kkendzia.myintranet.domain.notification;

import de.kkendzia.myintranet.domain._core.elements.AbstractAggregateRoot;
import de.kkendzia.myintranet.domain._core.elements.AbstractID;
import de.kkendzia.myintranet.domain._core.elements.association.SingleAssociation;
import de.kkendzia.myintranet.domain.user.EIUser;
import de.kkendzia.myintranet.domain.user.EIUser.EIUserID;

import java.time.LocalDateTime;
import java.util.UUID;

import static de.kkendzia.myintranet.domain._core.elements.association.SingleAssociation.requiredSingleLink;

public class UserNotification extends AbstractAggregateRoot<UserNotification, UserNotification.UserNotificationID>
{
    public static final String PROPERTY_TIMESTAMP = "timestamp";

    private final SingleAssociation<EIUser, EIUserID> user;
    private final String title;
    private final String description;
    private final LocalDateTime timestamp;

    public UserNotification(
            UserNotificationID id,
            EIUser user,
            final String title,
            final String description,
            final LocalDateTime timestamp)
    {
        super(id);
        this.user = requiredSingleLink(user);
        this.title = title;
        this.description = description;
        this.timestamp = timestamp;
    }

    public SingleAssociation<EIUser, EIUserID> getUser()
    {
        return user;
    }

    public String getTitle()
    {
        return title;
    }

    public String getDescription()
    {
        return description;
    }

    public LocalDateTime getTimestamp()
    {
        return timestamp;
    }

    //region TYPES
    public static class UserNotificationID extends AbstractID
    {
        public UserNotificationID(final UUID value)
        {
            super(value);
        }

        public UserNotificationID(final String value)
        {
            super(value);
        }

        public UserNotificationID()
        {
        }
    }
    //endregion
}
