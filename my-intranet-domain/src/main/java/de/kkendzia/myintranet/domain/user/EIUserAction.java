package de.kkendzia.myintranet.domain.user;

import de.kkendzia.myintranet.domain._core.AbstractEntity;

public class EIUserAction extends AbstractEntity
{
    private String title;
    private String route;

    public EIUserAction(final String title, final String route)
    {
        this.title = title;
        this.route = route;
    }

    public EIUserAction(final long id, final String title, final String route)
    {
        super(id);
        this.title = title;
        this.route = route;
    }

    public EIUserAction()
    {
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
