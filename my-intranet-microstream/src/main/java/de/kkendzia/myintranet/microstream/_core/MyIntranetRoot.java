package de.kkendzia.myintranet.microstream._core;

import de.kkendzia.myintranet.domain.ah.Ah;
import de.kkendzia.myintranet.domain.ah.Ah.AhID;
import de.kkendzia.myintranet.domain.mandant.Mandant;
import de.kkendzia.myintranet.domain.mandant.Mandant.MandantID;
import de.kkendzia.myintranet.domain.news.News;
import de.kkendzia.myintranet.domain.news.News.NewsID;
import de.kkendzia.myintranet.domain.notification.UserNotification;
import de.kkendzia.myintranet.domain.notification.UserNotification.UserNotificationID;
import de.kkendzia.myintranet.domain.permission.Permission;
import de.kkendzia.myintranet.domain.permission.Permission.PermissionID;
import de.kkendzia.myintranet.domain.role.Role;
import de.kkendzia.myintranet.domain.user.EIUser;
import de.kkendzia.myintranet.domain.user.EIUser.EIUserID;
import one.microstream.integrations.spring.boot.types.Storage;

import java.util.HashMap;
import java.util.Map;

@Storage
public class MyIntranetRoot
{
    // CORE
    private boolean init;

    // MANDANT
    private final Map<MandantID, Mandant> mandanten = new HashMap<>();

    // AUTH / USER
    private final Map<EIUserID, EIUser> eiUsers = new HashMap<>();
    private final Map<Role.RoleID, Role> roles = new HashMap<>();
    private final Map<PermissionID, Permission> permissions = new HashMap<>();

    // AH
    private final Map<AhID, Ah> ahs = new HashMap<>();

    // NEWS & NOTIFICATIONS
    private final Map<NewsID, News> news = new HashMap<>();
    private final Map<UserNotificationID, UserNotification> notifications = new HashMap<>();

    public boolean isInit()
    {
        return init;
    }

    public void setInit(boolean init)
    {
        this.init = init;
    }

    public Map<MandantID, Mandant> getMandanten()
    {
        return mandanten;
    }

    public Map<EIUserID, EIUser> getEiUsers()
    {
        return eiUsers;
    }

    public Map<Role.RoleID, Role> getRoles()
    {
        return roles;
    }

    public Map<PermissionID, Permission> getPermissions()
    {
        return permissions;
    }

    public Map<AhID, Ah> getAhs()
    {
        return ahs;
    }

    public Map<NewsID, News> getNews()
    {
        return news;
    }

    public Map<UserNotificationID, UserNotification> getNotifications()
    {
        return notifications;
    }
}
