package de.kkendzia.myintranet.microstream._core;

import de.kkendzia.myintranet.domain.ah.Ah;
import de.kkendzia.myintranet.domain.ah.Ah.AhID;
import de.kkendzia.myintranet.domain.mandant.Mandant;
import de.kkendzia.myintranet.domain.mandant.Mandant.MandantID;
import de.kkendzia.myintranet.domain.mandant.MandantSetting;
import de.kkendzia.myintranet.domain.permission.Permission;
import de.kkendzia.myintranet.domain.permission.Permission.PermissionID;
import de.kkendzia.myintranet.domain.role.Role;
import de.kkendzia.myintranet.domain.user.EIUser;
import de.kkendzia.myintranet.domain.user.EIUser.EIUserID;
import one.microstream.integrations.spring.boot.types.Storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Objects.requireNonNull;

@Storage
public class MyIntranetRoot
{
    // CORE
    private boolean init;
    private long lastId;

    // MANDANT
    private Map<MandantID, Mandant> mandanten = new HashMap<>();

    // AUTH / USER
    private Map<EIUserID, EIUser> eiUsers = new HashMap<>();
    private Map<Role.RoleID,Role> roles = new HashMap<>();
    private Map<PermissionID, Permission> permissions = new HashMap<>();

    // AH
    private Map<AhID, Ah> ahs = new HashMap<>();

    public boolean isInit()
    {
        return init;
    }

    public void setInit(boolean init)
    {
        this.init = init;
    }

    public long getLastId()
    {
        return lastId;
    }

    public Map<MandantID, Mandant> getMandanten()
    {
        return mandanten;
    }

    public Map<EIUserID, EIUser> getEiUsers()
    {
        return eiUsers;
    }

    public Map<Role.RoleID, Role> getRoles()
    {
        return roles;
    }

    public Map<PermissionID, Permission> getPermissions()
    {
        return permissions;
    }

    public Map<AhID, Ah> getAhs()
    {
        return ahs;
    }
}
