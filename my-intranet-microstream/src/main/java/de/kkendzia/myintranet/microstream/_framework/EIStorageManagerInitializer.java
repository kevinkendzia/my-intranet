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
            cmdMediator.run(new CreatePermission(rmPermissionID, "read_mandant"));

            final PermissionID wmPermissionID = new PermissionID();
            cmdMediator.run(new CreatePermission(wmPermissionID, "write_mandant"));

            final PermissionID raPermissionID = new PermissionID();
            cmdMediator.run(new CreatePermission(raPermissionID, "read_ah"));

            final PermissionID waPermissionID = new PermissionID();
            cmdMediator.run(new CreatePermission(waPermissionID, "write_ah"));

            // ROLES

            final RoleID rootRoleId = new RoleID();
            cmdMediator.run(new CreateRole(rootRoleId, "root"));
            cmdMediator.run(new AssignPermission(rootRoleId, rmPermissionID));
            cmdMediator.run(new AssignPermission(rootRoleId, wmPermissionID));
            cmdMediator.run(new AssignPermission(rootRoleId, raPermissionID));
            cmdMediator.run(new AssignPermission(rootRoleId, waPermissionID));

            final RoleID mandantRoleId = new RoleID();
            cmdMediator.run(new CreateRole(mandantRoleId, "mandant"));
            cmdMediator.run(new AssignPermission(mandantRoleId, rmPermissionID));
            cmdMediator.run(new AssignPermission(mandantRoleId, wmPermissionID));

            final RoleID ahRoleId = new RoleID();
            cmdMediator.run(new CreateRole(ahRoleId, "ah"));
            cmdMediator.run(new AssignPermission(ahRoleId, raPermissionID));
            cmdMediator.run(new AssignPermission(ahRoleId, waPermissionID));

            // USERS

            final EIUserID rootUserId = new EIUserID();
            cmdMediator.run(new CreateUser(rootUserId, "root", "root"));
            cmdMediator.run(new AssignRole(rootUserId, rootRoleId));

            final EIUserID mandantUserId = new EIUserID();
            cmdMediator.run(new CreateUser(mandantUserId, "mandant", "mandant"));
            cmdMediator.run(new AssignRole(mandantUserId, mandantRoleId));

            final EIUserID ahUserId = new EIUserID();
            cmdMediator.run(new CreateUser(ahUserId, "ah", "ah"));
            cmdMediator.run(new AssignRole(ahUserId, ahRoleId));

            root.setInit(true);
            sm.storeRoot();
        }
    }
}
