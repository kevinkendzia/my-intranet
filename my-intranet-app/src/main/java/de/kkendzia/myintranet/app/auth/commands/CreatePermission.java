package de.kkendzia.myintranet.app.auth.commands;

import de.kkendzia.myintranet.app._framework.cqrs.command.CommandHandler;
import de.kkendzia.myintranet.app._framework.cqrs.command.CommandHandler.Command;
import de.kkendzia.myintranet.app._framework.result.VoidResult;
import de.kkendzia.myintranet.domain._core.repository.Repository;
import de.kkendzia.myintranet.domain.permission.Permission;
import de.kkendzia.myintranet.domain.permission.Permission.PermissionID;
import org.springframework.stereotype.Component;

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

        public CreatePermissionHandlerImpl(Repository<Permission, PermissionID> permissionRepository)
        {
            this.permissionRepository = requireNonNull(permissionRepository, "permissionRepository can't be null!");
        }

        @Override
        public VoidResult<Failure> run(final CreatePermission command)
        {
            permissionRepository.add(new Permission(command.permissionId(), command.name()));
            return VoidResult.success();
        }
    }

    public enum Failure
    {
        ALREADY_EXISTS
    }
}
