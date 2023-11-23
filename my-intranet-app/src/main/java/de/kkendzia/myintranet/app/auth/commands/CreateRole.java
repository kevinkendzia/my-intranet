package de.kkendzia.myintranet.app.auth.commands;

import de.kkendzia.myintranet.app._framework.cqrs.command.CommandHandler;
import de.kkendzia.myintranet.app._framework.cqrs.command.CommandHandler.Command;
import de.kkendzia.myintranet.app._framework.result.VoidResult;
import de.kkendzia.myintranet.domain._core.repository.Repository;
import de.kkendzia.myintranet.domain.role.Role;
import de.kkendzia.myintranet.domain.role.Role.RoleID;
import org.springframework.stereotype.Component;

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
        public VoidResult<Failure> run(final CreateRole command)
        {
            roleRepository.add(
                    new Role(
                            command.roleId() != null ? command.roleId() : new RoleID(),
                            command.name()));

            return VoidResult.success();
        }
    }

    public enum Failure
    {
        ALREADY_EXISTS
    }
}
