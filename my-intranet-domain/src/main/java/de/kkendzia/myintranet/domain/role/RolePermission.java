package de.kkendzia.myintranet.domain.role;

import de.kkendzia.myintranet.domain._core.AbstractEntity;
import de.kkendzia.myintranet.domain._core.AbstractID;
import de.kkendzia.myintranet.domain.permission.Permission;

import java.util.UUID;

public class RolePermission extends AbstractEntity<Role, RolePermission.RolePermissionID>
{
    private Permission.PermissionID permissionId;

    public RolePermission(final Permission.PermissionID permissionId)
    {
        this.permissionId = permissionId;
    }

    public RolePermission(final RolePermissionID id, final Permission.PermissionID permissionId)
    {
        super(id);
        this.permissionId = permissionId;
    }

    //region TYPES
    public static class RolePermissionID extends AbstractID
    {
        public RolePermissionID(final UUID value)
        {
            super(value);
        }

        public RolePermissionID()
        {
        }
    }
    //endregion
}
