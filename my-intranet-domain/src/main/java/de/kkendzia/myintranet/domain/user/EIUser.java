package de.kkendzia.myintranet.domain.user;

import de.kkendzia.myintranet.domain._core.AbstractAggregateRoot;
import de.kkendzia.myintranet.domain._core.AbstractID;
import de.kkendzia.myintranet.domain._core.association.MultiAssociation;
import de.kkendzia.myintranet.domain.news.News;
import de.kkendzia.myintranet.domain.news.News.NewsID;
import de.kkendzia.myintranet.domain.notification.UserNotification;
import de.kkendzia.myintranet.domain.notification.UserNotification.UserNotificationID;
import de.kkendzia.myintranet.domain.role.Role;
import de.kkendzia.myintranet.domain.role.Role.RoleID;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static de.kkendzia.myintranet.domain._core.association.MultiAssociation.emptyMultLink;
import static java.util.Objects.requireNonNull;

public class EIUser extends AbstractAggregateRoot<EIUser, EIUser.EIUserID>
{
    private String userName = "";
    private String firstName = "";
    private String lastName = "";
    private String password = "";
    private final List<EIUserAction> favoriteActions = new ArrayList<>();
    private final List<EIUserAction> recentActions = new ArrayList<>();

    // ASSOCIATIONS
    private final MultiAssociation<Role, RoleID> roles = emptyMultLink();
    private final MultiAssociation<News, NewsID> news = emptyMultLink();
    private final MultiAssociation<UserNotification, UserNotificationID> notifications = emptyMultLink();

    public EIUser(
            EIUserID id,
            final String userName,
            final String firstName,
            final String lastName,
            final String password)
    {
        super(id);
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
    }

    public void addFavoriteAction(EIUserAction action)
    {
        requireNonNull(action, "action can't be null!");
        favoriteActions.add(action);
    }

    public void addRecentAction(EIUserAction action)
    {
        requireNonNull(action, "action can't be null!");
        recentActions.add(action);
    }

    public void addNotification(UserNotification notification)
    {
        requireNonNull(notification, "notification can't be null!");
        notifications.add(notification);
    }

    // TODO: changePassword(), rename(), etc.


    //region GETTER
    public String getUserName()
    {
        return userName;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public String getPassword()
    {
        return password;
    }

    public List<EIUserAction> getFavoriteActions()
    {
        return favoriteActions;
    }

    public List<EIUserAction> getRecentActions()
    {
        return recentActions;
    }

    public MultiAssociation<News, NewsID> getNews()
    {
        return news;
    }

    public void readNews(final News news)
    {
        // TODO: validation
        this.news.add(news);
    }

    public MultiAssociation<Role, RoleID> getRoles()
    {
        return roles;
    }

    public void assignRole(final Role role)
    {
        // TODO: validation
        roles.add(role);
    }

    //endregion

    //region TYPES
    public static class EIUserID extends AbstractID
    {
        public EIUserID(final UUID value)
        {
            super(value);
        }

        public EIUserID()
        {
        }
    }
    //endregion
}
