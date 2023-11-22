package de.kkendzia.myintranet.app.useractions.commands;

import de.kkendzia.myintranet.app._framework.cqrs.CommandHandler;
import de.kkendzia.myintranet.app._framework.cqrs.CommandHandler.Command;
import de.kkendzia.myintranet.app.useractions.shared.ActionItem;
import de.kkendzia.myintranet.domain._core.repository.Repository;
import de.kkendzia.myintranet.domain.user.EIUser;
import de.kkendzia.myintranet.domain.user.EIUser.EIUserID;
import de.kkendzia.myintranet.domain.user.EIUserAction;
import org.springframework.stereotype.Component;

import static de.kkendzia.myintranet.app._framework.cqrs.CommandHandler.CommandResult.success;
import static java.util.Objects.requireNonNull;

public record AddRecentAction(EIUserID userId, ActionItem item) implements Command<AddRecentAction.Failure>
{
    interface AddRecentActionHandler extends CommandHandler<AddRecentAction, Failure>
    {
        @Override
        default Class<AddRecentAction> getCommandClass()
        {
            return AddRecentAction.class;
        }
    }

    @Component
    public static class AddRecentActionHandlerImpl implements AddRecentActionHandler
    {
        private Repository<EIUser, EIUserID> repository;

        public AddRecentActionHandlerImpl(final Repository<EIUser, EIUserID> repository)
        {
            this.repository = requireNonNull(repository, "repository can't be null!");
        }

        @Override
        public CommandResult<Failure> executeResult(final AddRecentAction command)
        {
            final EIUser user = repository.getByID(command.userId());
            user.addRecentAction(new EIUserAction(command.item().title(), command.item().route()));
            repository.update(user);
            return success();
        }
    }

    public enum Failure
    {
        UNKNOWN
    }
}
