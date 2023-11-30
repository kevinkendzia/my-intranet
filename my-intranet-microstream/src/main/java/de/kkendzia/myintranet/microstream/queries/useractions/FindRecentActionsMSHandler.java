package de.kkendzia.myintranet.microstream.queries.useractions;

import de.kkendzia.myintranet.app._framework.cqrs.query.paged.Paging;
import de.kkendzia.myintranet.app._framework.result.ListResult;
import de.kkendzia.myintranet.app.useractions.queries.FindRecentActions;
import de.kkendzia.myintranet.app.useractions.shared.ActionItem;
import de.kkendzia.myintranet.domain.user.EIUser;
import de.kkendzia.myintranet.domain.user.EIUserAction;
import de.kkendzia.myintranet.microstream._framework.AbstractMSQueryHandler;
import one.microstream.storage.types.StorageManager;
import org.springframework.stereotype.Component;

import static java.util.Comparator.comparing;

@Component
public class FindRecentActionsMSHandler extends AbstractMSQueryHandler<EIUserAction>
        implements FindRecentActions.FindRecentActionsHandler
{
    public FindRecentActionsMSHandler(final StorageManager storageManager)
    {
        super(storageManager);
        registerSortOrder(EIUserAction.PROPERTY_TIMESTAMP, comparing(EIUserAction::getTimestamp));
    }

    @Override
    public ListResult<ActionItem, FindRecentActions.Failure> fetchAll(
            final FindRecentActions query,
            final Paging paging)
    {
        final EIUser user = getRoot().getEiUsers().get(query.userId());

        final var result =
                applyPaging(user.getRecentActions().stream(), paging)
                        .map(i -> new ActionItem(i.getTitle(), i.getRoute(), i.getTimestamp()))
                        .toList();
        return ListResult.success(result);
    }
}
