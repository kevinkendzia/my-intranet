package de.kkendzia.myintranet.domain.user.auth;

import de.kkendzia.myintranet.domain._core.AbstractEntity;

public class UserRole extends AbstractEntity
{
    private long userId;
    private long roleId;

    public UserRole()
    {
    }

    public UserRole(long id)
    {
        super(id);
    }

    public long getUserId()
    {
        return userId;
    }

    public void setUserId(long userId)
    {
        this.userId = userId;
    }

    public long getRoleId()
    {
        return roleId;
    }

    public void setRoleId(long roleId)
    {
        this.roleId = roleId;
    }
}
