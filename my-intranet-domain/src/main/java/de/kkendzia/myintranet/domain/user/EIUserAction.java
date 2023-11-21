package de.kkendzia.myintranet.domain.user;

import de.kkendzia.myintranet.domain._core.ValueObject;

public class EIUserAction implements ValueObject
{
    private String title;
    private String route;

    public EIUserAction(final String title, final String route)
    {
        this.title = title;
        this.route = route;
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
}
