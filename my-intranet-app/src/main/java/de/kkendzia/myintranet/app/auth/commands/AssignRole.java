package de.kkendzia.myintranet.app.auth.commands;

import de.kkendzia.myintranet.app._framework.cqrs.CommandHandler;
import de.kkendzia.myintranet.app._framework.cqrs.CommandHandler.Command;
import de.kkendzia.myintranet.domain._core.repository.Repository;
import de.kkendzia.myintranet.domain.role.Role;
import de.kkendzia.myintranet.domain.role.Role.RoleID;
import de.kkendzia.myintranet.domain.user.EIUser;
import de.kkendzia.myintranet.domain.user.EIUser.EIUserID;
import org.springframework.stereotype.Component;

import static de.kkendzia.myintranet.app._framework.cqrs.CommandHandler.CommandResult.success;
import static java.util.Objects.requireNonNull;

public record AssignRole(
        EIUserID userId,
        RoleID roleId)
        implements Command<AssignRole.Failure>
{
    interface AssignRoleHandler extends CommandHandler<AssignRole, Failure>
    {
        @Override
        default Class<AssignRole> getCommandClass()
        {
            return AssignRole.class;
        }
    }

    @Component
    public static class AssignRoleHandlerImpl implements AssignRoleHandler
    {
        private final Repository<Role, RoleID> roleRepository;
        private final Repository<EIUser, EIUserID> userRepository;

        public AssignRoleHandlerImpl(
                Repository<Role, RoleID> roleRepository,
                final Repository<EIUser, EIUserID> userRepository)
        {
            this.roleRepository = requireNonNull(roleRepository, "roleRepository can't be null!");
            this.userRepository = requireNonNull(userRepository, "userRepository can't be null!");
        }

        @Override
        public CommandResult<Failure> executeResult(final AssignRole command)
        {
            final Role role = roleRepository.getByID(command.roleId());
            final EIUser user = userRepository.getByID(command.userId());

            user.assignRole(role);
            userRepository.update(user);

            return success();
        }
    }

    public enum Failure
    {
        ALREADY_EXISTS
    }
}
