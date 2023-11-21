package de.kkendzia.myintranet.app.user.commands.addrecentaction;

import de.kkendzia.myintranet.app._framework.cqrs.CommandHandler;
import de.kkendzia.myintranet.domain._core.repository.AggregateLookup;
import de.kkendzia.myintranet.domain._core.repository.AggregateUpdate;
import de.kkendzia.myintranet.domain.user.EIUser;
import de.kkendzia.myintranet.domain.user.EIUserAction;
import org.springframework.stereotype.Service;

import static java.util.Objects.requireNonNull;

@Service
public class AddRecentActionCommandHandler implements CommandHandler<AddRecentActionCommand>
{
    private AggregateLookup<EIUser, EIUser.EIUserID> lookup;
    private AggregateUpdate<EIUser, EIUser.EIUserID> update;

    public AddRecentActionCommandHandler(
            final AggregateLookup<EIUser, EIUser.EIUserID> lookup,
            final AggregateUpdate<EIUser, EIUser.EIUserID> update)
    {
        this.lookup = requireNonNull(lookup, "lookup can't be null!");
        this.update = requireNonNull(update, "update can't be null!");
    }

    @Override
    public void execute(final AddRecentActionCommand command)
    {
        final EIUser user = lookup.getByID(command.userId());
        user.addRecentAction(new EIUserAction(command.item().title(), command.item().route()));
        update.update(user);
    }
}
