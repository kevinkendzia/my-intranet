package de.kkendzia.myintranet.domain.user;

import de.kkendzia.myintranet.domain._framework.AbstractEntity;

public class RolePermission extends AbstractEntity
{
    private long roleId;
    private long permissionId;

    public RolePermission()
    {
    }

    public RolePermission(long id)
    {
        super(id);
    }

    public long getRoleId()
    {
        return roleId;
    }

    public void setRoleId(long roleId)
    {
        this.roleId = roleId;
    }

    public long getPermissionId()
    {
        return permissionId;
    }

    public void setPermissionId(long permissionId)
    {
        this.permissionId = permissionId;
    }
}
