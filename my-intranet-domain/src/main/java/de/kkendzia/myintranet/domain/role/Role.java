package de.kkendzia.myintranet.domain.role;

import de.kkendzia.myintranet.domain._core.elements.AbstractAggregateRoot;
import de.kkendzia.myintranet.domain._core.elements.AbstractID;
import de.kkendzia.myintranet.domain.permission.Permission.PermissionID;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Role extends AbstractAggregateRoot<Role, Role.RoleID>
{
    private final String name;
    private final List<RolePermission> permissions = new ArrayList<>();

    public Role(final String name)
    {
        this.name = name;
    }

    public Role(final RoleID id, final String name)
    {
        super(id);
        this.name = name;
    }

    public void assignPermission(PermissionID permissionId)
    {
        // TODO: validate existing?
        permissions.add(new RolePermission(permissionId));
    }

    //region GETTER
    public String getName()
    {
        return name;
    }

    public List<RolePermission> getPermissions()
    {
        return permissions;
    }
    //endregion

    //region TYPES
    public static class RoleID extends AbstractID
    {
        public RoleID(final UUID value)
        {
            super(value);
        }

        public RoleID()
        {
        }
    }
    //endregion
}
