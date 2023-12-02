package de.kkendzia.myintranet.app.useractions.commands;

import de.kkendzia.myintranet.app._framework.cqrs.command.CommandHandler;
import de.kkendzia.myintranet.app._framework.cqrs.command.CommandHandler.Command;
import de.kkendzia.myintranet.app._framework.result.VoidResult;
import de.kkendzia.myintranet.app.useractions._shared.ActionItem;
import de.kkendzia.myintranet.domain.user.EIUser;
import de.kkendzia.myintranet.domain.user.EIUser.EIUserID;
import de.kkendzia.myintranet.domain.user.EIUserAction;
import de.kkendzia.myintranet.domain.user.EIUserRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static de.kkendzia.myintranet.app._framework.result.VoidResult.success;
import static java.util.Objects.requireNonNull;

public record AddRecentAction(
        EIUserID userId,
        ActionItem item) implements Command<AddRecentAction.Failure>
{
    public AddRecentAction
    {
        requireNonNull(userId, "userId can't be null!");
        requireNonNull(item, "item can't be null!");
    }

    public interface AddRecentActionHandler extends CommandHandler<AddRecentAction, Failure>
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
        private final EIUserRepository repository;

        public AddRecentActionHandlerImpl(final EIUserRepository repository)
        {
            this.repository = requireNonNull(repository, "repository can't be null!");
        }

        @Override
        public VoidResult<Failure> run(final AddRecentAction command)
        {
            final EIUser user = repository.getByID(command.userId());

            final var title = command.item().getTitle();
            final var route = command.item().getRoute();

            final var recent = user.getRecentActions();
            final var last = recent.isEmpty() ? null : recent.get(recent.size() - 1);
            if (last == null || !Objects.equals(last.getRoute(), route))
            {
                user.addRecentAction(new EIUserAction(title, route));
            }

            while (recent.size() > 5)
            {
                recent.remove(0);
            }

            repository.update(user);
            return success();
        }
    }

    public enum Failure
    {
        UNKNOWN
    }
}
