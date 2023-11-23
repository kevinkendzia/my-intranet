package de.kkendzia.myintranet.microstream._framework;

import de.kkendzia.myintranet.app._framework.cqrs.command.CommandMediator;
import de.kkendzia.myintranet.app.auth.commands.*;
import de.kkendzia.myintranet.domain.permission.Permission.PermissionID;
import de.kkendzia.myintranet.domain.role.Role.RoleID;
import de.kkendzia.myintranet.domain.user.EIUser.EIUserID;
import de.kkendzia.myintranet.microstream._core.MyIntranetRoot;
import one.microstream.integrations.spring.boot.types.config.StorageManagerInitializer;
import one.microstream.storage.types.StorageManager;
import org.springframework.stereotype.Component;

import static java.util.Objects.requireNonNull;

@Component
public class EIStorageManagerInitializer implements StorageManagerInitializer
{
    private final CommandMediator cmdMediator;

    public EIStorageManagerInitializer(final CommandMediator cmdMediator)
    {
        this.cmdMediator = requireNonNull(cmdMediator, "cmdMediator can't be null!");
    }

    @Override
    public void initialize(StorageManager sm)
    {
        MyIntranetRoot root = (MyIntranetRoot) sm.root();
        if (!root.isInit())
        {
            // PERMISSIONS

            final PermissionID rmPermissionID = new PermissionID();
            cmdMediator.execute(new CreatePermission(rmPermissionID, "read_mandant"));

            final PermissionID wmPermissionID = new PermissionID();
            cmdMediator.execute(new CreatePermission(wmPermissionID, "write_mandant"));

            final PermissionID raPermissionID = new PermissionID();
            cmdMediator.execute(new CreatePermission(raPermissionID, "read_ah"));

            final PermissionID waPermissionID = new PermissionID();
            cmdMediator.execute(new CreatePermission(waPermissionID, "write_ah"));

            // ROLES

            final RoleID rootRoleId = new RoleID();
            cmdMediator.execute(new CreateRole(rootRoleId, "root"));
            cmdMediator.execute(new AssignPermission(rootRoleId, rmPermissionID));
            cmdMediator.execute(new AssignPermission(rootRoleId, wmPermissionID));
            cmdMediator.execute(new AssignPermission(rootRoleId, raPermissionID));
            cmdMediator.execute(new AssignPermission(rootRoleId, waPermissionID));

            final RoleID mandantRoleId = new RoleID();
            cmdMediator.execute(new CreateRole(mandantRoleId, "mandant"));
            cmdMediator.execute(new AssignPermission(mandantRoleId, rmPermissionID));
            cmdMediator.execute(new AssignPermission(mandantRoleId, wmPermissionID));

            final RoleID ahRoleId = new RoleID();
            cmdMediator.execute(new CreateRole(ahRoleId, "ah"));
            cmdMediator.execute(new AssignPermission(ahRoleId, raPermissionID));
            cmdMediator.execute(new AssignPermission(ahRoleId, waPermissionID));

            // USERS

            final EIUserID rootUserId = new EIUserID();
            cmdMediator.execute(new CreateUser(rootUserId, "root", "root"));
            cmdMediator.execute(new AssignRole(rootUserId, rootRoleId));

            final EIUserID mandantUserId = new EIUserID();
            cmdMediator.execute(new CreateUser(mandantUserId, "mandant", "mandant"));
            cmdMediator.execute(new AssignRole(mandantUserId, mandantRoleId));

            final EIUserID ahUserId = new EIUserID();
            cmdMediator.execute(new CreateUser(ahUserId, "ah", "ah"));
            cmdMediator.execute(new AssignRole(ahUserId, ahRoleId));

            root.setInit(true);
            sm.storeRoot();
        }
    }
}
