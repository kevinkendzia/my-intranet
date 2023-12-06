package de.kkendzia.myintranet.domain.permission;

import de.kkendzia.myintranet.domain._core.elements.AbstractAggregateRoot;
import de.kkendzia.myintranet.domain._core.elements.AbstractID;

import java.util.UUID;

public class Permission extends AbstractAggregateRoot<Permission, Permission.PermissionID>
{
    public static final String ROOT = "root";

    private String name;

    public Permission(final String name)
    {
        this.name = name;
    }

    public Permission(final PermissionID id, final String name)
    {
        super(id);
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    //region TYPES
    public static class PermissionID extends AbstractID
    {
        public PermissionID(final UUID value)
        {
            super(value);
        }

        public PermissionID()
        {
        }
    }
    //endregion
}
