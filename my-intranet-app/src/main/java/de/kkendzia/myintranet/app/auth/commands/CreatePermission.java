package de.kkendzia.myintranet.app.auth.commands;

import de.kkendzia.myintranet.app._framework.cqrs.CommandHandler;
import de.kkendzia.myintranet.app._framework.cqrs.CommandHandler.Command;
import de.kkendzia.myintranet.domain._core.repository.Repository;
import de.kkendzia.myintranet.domain.permission.Permission;
import de.kkendzia.myintranet.domain.permission.Permission.PermissionID;
import de.kkendzia.myintranet.domain.role.Role;
import de.kkendzia.myintranet.domain.role.Role.RoleID;
import org.springframework.stereotype.Component;

import static de.kkendzia.myintranet.app._framework.cqrs.CommandHandler.CommandResult.success;
import static java.util.Objects.requireNonNull;

public record CreatePermission(
        PermissionID permissionId,
        String name)
        implements Command<CreatePermission.Failure>
{
    interface CreatePermissionHandler extends CommandHandler<CreatePermission, Failure>
    {
        @Override
        default Class<CreatePermission> getCommandClass()
        {
            return CreatePermission.class;
        }
    }

    @Component
    public static class CreatePermissionHandlerImpl implements CreatePermissionHandler
    {
        private final Repository<Permission, PermissionID> permissionRepository;

        public CreatePermissionHandlerImpl(                Repository<Permission, PermissionID> permissionRepository)
        {
            this.permissionRepository = requireNonNull(permissionRepository, "permissionRepository can't be null!");
        }

        @Override
        public CommandResult<Failure> executeResult(final CreatePermission command)
        {
            permissionRepository.add(new Permission(command.permissionId(), command.name()));
            return success();
        }
    }

    public enum Failure
    {
        ALREADY_EXISTS
    }
}
