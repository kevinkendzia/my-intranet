package de.kkendzia.myintranet.domain.user;

import de.kkendzia.myintranet.domain._framework.AbstractEntity;

public class Permission extends AbstractEntity
{
    public static final String ROOT = "root";

    private String name;

    public Permission()
    {
    }

    public Permission(long id)
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