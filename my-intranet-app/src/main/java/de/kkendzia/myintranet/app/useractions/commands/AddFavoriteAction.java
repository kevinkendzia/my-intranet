package de.kkendzia.myintranet.app.useractions.commands;

import de.kkendzia.myintranet.app._framework.cqrs.command.CommandHandler;
import de.kkendzia.myintranet.app._framework.cqrs.command.CommandHandler.Command;
import de.kkendzia.myintranet.app._framework.result.VoidResult;
import de.kkendzia.myintranet.app.useractions.shared.ActionItem;
import de.kkendzia.myintranet.domain._core.repository.Repository;
import de.kkendzia.myintranet.domain.user.EIUser;
import de.kkendzia.myintranet.domain.user.EIUser.EIUserID;
import de.kkendzia.myintranet.domain.user.EIUserAction;
import org.springframework.stereotype.Component;

import static de.kkendzia.myintranet.app._framework.result.VoidResult.success;
import static java.util.Objects.requireNonNull;

public record AddFavoriteAction(
        EIUserID userId,
        ActionItem item) implements Command<AddFavoriteAction.Failure>
{
    interface AddFavoriteActionHandler extends CommandHandler<AddFavoriteAction, Failure>
    {
        @Override
        default Class<AddFavoriteAction> getCommandClass()
        {
            return AddFavoriteAction.class;
        }
    }

    @Component
    public static class AddFavoriteActionHandlerImpl implements AddFavoriteActionHandler
    {
        private final Repository<EIUser, EIUserID> repository;

        public AddFavoriteActionHandlerImpl(final Repository<EIUser, EIUserID> repository)
        {
            this.repository = requireNonNull(repository, "repository can't be null!");
        }

        @Override
        public VoidResult<Failure> run(final AddFavoriteAction command)
        {
            final EIUser user = repository.getByID(command.userId());
            user.addFavoriteAction(new EIUserAction(command.item().getTitle(), command.item().getRoute()));
            if (user.getFavoriteActions().size() > 5)
            {
                user.getFavoriteActions().remove(0);
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
