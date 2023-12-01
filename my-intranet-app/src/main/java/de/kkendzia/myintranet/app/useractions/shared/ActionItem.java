package de.kkendzia.myintranet.app.useractions.shared;

import java.time.LocalDateTime;

public final class ActionItem
{
    private String title;
    private String route;
    private LocalDateTime timestamp;

    public ActionItem(
            String title,
            String route,
            LocalDateTime timestamp)
    {
        this.title = title;
        this.route = route;
        this.timestamp = timestamp;
    }

    public ActionItem(final String title, final String route)
    {
        this(title, route, LocalDateTime.now());
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(final String title)
    {
        this.title = title;
    }

    public String getRoute()
    {
        return route;
    }

    public void setRoute(final String route)
    {
        this.route = route;
    }

    public LocalDateTime getTimestamp()
    {
        return timestamp;
    }

    public void setTimestamp(final LocalDateTime timestamp)
    {
        this.timestamp = timestamp;
    }
}
