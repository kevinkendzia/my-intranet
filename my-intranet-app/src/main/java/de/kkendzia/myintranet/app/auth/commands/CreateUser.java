package de.kkendzia.myintranet.app.auth.commands;

import de.kkendzia.myintranet.app._framework.cqrs.CommandHandler;
import de.kkendzia.myintranet.app._framework.cqrs.CommandHandler.Command;
import de.kkendzia.myintranet.domain._core.repository.Repository;
import de.kkendzia.myintranet.domain.user.EIUser;
import de.kkendzia.myintranet.domain.user.EIUser.EIUserID;
import org.springframework.stereotype.Component;

import static de.kkendzia.myintranet.app._framework.cqrs.CommandHandler.CommandResult.success;
import static java.util.Objects.requireNonNull;

public record CreateUser(
        EIUserID userId,
        String userName,
        String firstName,
        String lastName,
        String password) implements Command<CreateUser.Failure>
{
    public CreateUser(EIUserID userId, final String userName, final String password)
    {
        this(userId, userName, null, null, password);
    }

    interface CreateUserHandler extends CommandHandler<CreateUser, Failure>
    {
        @Override
        default Class<CreateUser> getCommandClass()
        {
            return CreateUser.class;
        }
    }

    @Component
    public static class CreateUserHandlerImpl implements CreateUserHandler
    {
        private final Repository<EIUser, EIUserID> repository;

        public CreateUserHandlerImpl(final Repository<EIUser, EIUserID> repository)
        {
            this.repository = requireNonNull(repository, "repository can't be null!");
        }

        @Override
        public CommandResult<Failure> executeResult(final CreateUser command)
        {
            final EIUser user = new EIUser(
                    command.userId() != null ? command.userId() : new EIUserID(),
                    command.userName(),
                    command.firstName(),
                    command.lastName(),
                    command.password());

            repository.add(user);

            return success();
        }
    }

    public enum Failure
    {
        ALREADY_EXISTS
    }
}
