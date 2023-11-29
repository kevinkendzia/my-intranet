package de.kkendzia.myintranet.domain.user;

import de.kkendzia.myintranet.domain._core.ValueObject;

import java.time.LocalDateTime;

public class EIUserAction implements ValueObject
{
    public static final String PROPERTY_TIMESTAMP = "timestamp";

    private String title;
    private String route;
    private LocalDateTime timestamp;

    public EIUserAction(final String title, final String route, LocalDateTime timestamp)
    {
        this.title = title;
        this.route = route;
        this.timestamp = timestamp;
    }

    public EIUserAction(final String title, final String route)
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
