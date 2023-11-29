package de.kkendzia.myintranet.ei.core;

import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinServiceInitListener;
import de.kkendzia.myintranet.app._framework.cqrs.command.CommandMediator;
import de.kkendzia.myintranet.app._framework.cqrs.query.QueryMediator;
import de.kkendzia.myintranet.app.auth.commands.*;
import de.kkendzia.myintranet.app.init.commands.SetAppInit;
import de.kkendzia.myintranet.app.init.queries.IsAppInit;
import de.kkendzia.myintranet.domain.permission.Permission;
import de.kkendzia.myintranet.domain.role.Role;
import de.kkendzia.myintranet.domain.user.EIUser;
import de.kkendzia.myintranet.ei.ui.errors.EIErrorHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import static java.util.Objects.requireNonNull;

@Component
public class EIInitListener implements VaadinServiceInitListener
{
    private static final Logger LOGGER = LoggerFactory.getLogger(EIInitListener.class);

    private final transient QueryMediator quMediator;
    private final transient CommandMediator cmdMediator;

    public EIInitListener(final QueryMediator quMediator, final CommandMediator cmdMediator)
    {
        this.quMediator = requireNonNull(quMediator, "quMediator can't be null!");
        this.cmdMediator = requireNonNull(cmdMediator, "cmdMediator can't be null!");
    }

    @Override
    public void serviceInit(ServiceInitEvent event)
    {
        initDB();
        VaadinService service = event.getSource();

        service.addSessionInitListener(e ->
        {
            /*
             * 16.10.2023 KK:
             * Only triggered on new sessions!
             * Clear Browser-Data (Cookies) if event isn't triggering!
             */
            e.getSession().setErrorHandler(new EIErrorHandler());
        });

        LOGGER.info("EI-Service initialized!");
    }

    private void initDB()
    {
        if (!quMediator.test(new IsAppInit()))
        {
            LOGGER.info("DB wasn't initialized yet! Initializing...");

            // PERMISSIONS

            final Permission.PermissionID rmPermissionID = new Permission.PermissionID();
            cmdMediator.run(new CreatePermission(rmPermissionID, "read_mandant"));

            final Permission.PermissionID wmPermissionID = new Permission.PermissionID();
            cmdMediator.run(new CreatePermission(wmPermissionID, "write_mandant"));

            final Permission.PermissionID raPermissionID = new Permission.PermissionID();
            cmdMediator.run(new CreatePermission(raPermissionID, "read_ah"));

            final Permission.PermissionID waPermissionID = new Permission.PermissionID();
            cmdMediator.run(new CreatePermission(waPermissionID, "write_ah"));

            // ROLES

            final Role.RoleID rootRoleId = new Role.RoleID();
            cmdMediator.run(new CreateRole(rootRoleId, "root"));
            cmdMediator.run(new AssignPermission(rootRoleId, rmPermissionID));
            cmdMediator.run(new AssignPermission(rootRoleId, wmPermissionID));
            cmdMediator.run(new AssignPermission(rootRoleId, raPermissionID));
            cmdMediator.run(new AssignPermission(rootRoleId, waPermissionID));

            final Role.RoleID mandantRoleId = new Role.RoleID();
            cmdMediator.run(new CreateRole(mandantRoleId, "mandant"));
            cmdMediator.run(new AssignPermission(mandantRoleId, rmPermissionID));
            cmdMediator.run(new AssignPermission(mandantRoleId, wmPermissionID));

            final Role.RoleID ahRoleId = new Role.RoleID();
            cmdMediator.run(new CreateRole(ahRoleId, "ah"));
            cmdMediator.run(new AssignPermission(ahRoleId, raPermissionID));
            cmdMediator.run(new AssignPermission(ahRoleId, waPermissionID));

            // USERS

            final EIUser.EIUserID rootUserId = new EIUser.EIUserID();
            cmdMediator.run(new CreateUser(rootUserId, "root", "root"));
            cmdMediator.run(new AssignRole(rootUserId, rootRoleId));

            final EIUser.EIUserID mandantUserId = new EIUser.EIUserID();
            cmdMediator.run(new CreateUser(mandantUserId, "mandant", "mandant"));
            cmdMediator.run(new AssignRole(mandantUserId, mandantRoleId));

            final EIUser.EIUserID ahUserId = new EIUser.EIUserID();
            cmdMediator.run(new CreateUser(ahUserId, "ah", "ah"));
            cmdMediator.run(new AssignRole(ahUserId, ahRoleId));

            cmdMediator.run(new SetAppInit(true));
            LOGGER.info("DB was initialized successfully!");
        }
        else
        {
            LOGGER.info("DB was already initialized before!");
        }
    }
}
