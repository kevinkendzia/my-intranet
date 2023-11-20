package de.kkendzia.myintranet.domain.user.auth;

import de.kkendzia.myintranet.domain._core.AbstractEntity;

public class Role extends AbstractEntity
{
    private String name;

    public Role()
    {
    }

    public Role(long id)
    {
        super(id);
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
