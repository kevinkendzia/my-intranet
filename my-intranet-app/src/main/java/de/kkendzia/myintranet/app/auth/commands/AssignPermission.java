package de.kkendzia.myintranet.app.auth.commands;

import de.kkendzia.myintranet.app._framework.cqrs.command.CommandHandler;
import de.kkendzia.myintranet.app._framework.cqrs.command.CommandHandler.Command;
import de.kkendzia.myintranet.app._framework.result.VoidResult;
import de.kkendzia.myintranet.domain._core.repository.Repository;
import de.kkendzia.myintranet.domain.permission.Permission;
import de.kkendzia.myintranet.domain.permission.Permission.PermissionID;
import de.kkendzia.myintranet.domain.role.Role;
import de.kkendzia.myintranet.domain.role.Role.RoleID;
import org.springframework.stereotype.Component;

import static java.util.Objects.requireNonNull;

public record AssignPermission(
        RoleID roleId,
        PermissionID permissionId)
        implements Command<AssignPermission.Failure>
{
    interface AssignPermissionHandler extends CommandHandler<AssignPermission, Failure>
    {
        @Override
        default Class<AssignPermission> getCommandClass()
        {
            return AssignPermission.class;
        }
    }

    @Component
    public static class AssignPermissionHandlerImpl implements AssignPermissionHandler
    {
        private final Repository<Permission, PermissionID> permissionRepository;
        private final Repository<Role, RoleID> roleRepository;

        public AssignPermissionHandlerImpl(
                Repository<Permission, PermissionID> permissionRepository,
                Repository<Role, RoleID> roleRepository)
        {
            this.permissionRepository = requireNonNull(permissionRepository, "permissionRepository can't be null!");
            this.roleRepository = requireNonNull(roleRepository, "roleRepository can't be null!");
        }

        @Override
        public VoidResult<Failure> run(final AssignPermission command)
        {
            final Permission permission = permissionRepository.getByID(command.permissionId());
            final Role role = roleRepository.getByID(command.roleId());

            role.assignPermission(permission.getId());
            roleRepository.update(role);

            return VoidResult.success();
        }
    }

    public enum Failure
    {
        ALREADY_EXISTS
    }
}
