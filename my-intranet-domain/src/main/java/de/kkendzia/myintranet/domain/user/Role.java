package de.kkendzia.myintranet.domain.user;

import de.kkendzia.myintranet.domain._framework.AbstractEntity;

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
