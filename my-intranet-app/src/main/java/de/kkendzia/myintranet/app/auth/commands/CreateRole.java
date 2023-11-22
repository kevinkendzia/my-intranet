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

public record CreateRole(
        RoleID roleId,
        String name)
        implements Command<CreateRole.Failure>
{
    interface CreateRoleHandler extends CommandHandler<CreateRole, Failure>
    {
        @Override
        default Class<CreateRole> getCommandClass()
        {
            return CreateRole.class;
        }
    }

    @Component
    public static class CreateRoleHandlerImpl implements CreateRoleHandler
    {
        private final Repository<Role, RoleID> roleRepository;

        public CreateRoleHandlerImpl(Repository<Role, RoleID> roleRepository)
        {
            this.roleRepository = requireNonNull(roleRepository, "roleRepository can't be null!");
        }

        @Override
        public CommandResult<Failure> executeResult(final CreateRole command)
        {
            roleRepository.add(
                    new Role(
                            command.roleId() != null ? command.roleId() : new RoleID(),
                            command.name()));

            return success();
        }
    }

    public enum Failure
    {
        ALREADY_EXISTS
    }
}
