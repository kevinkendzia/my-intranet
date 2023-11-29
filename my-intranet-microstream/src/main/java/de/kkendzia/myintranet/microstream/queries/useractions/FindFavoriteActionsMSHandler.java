package de.kkendzia.myintranet.microstream.queries.useractions;

import de.kkendzia.myintranet.app._framework.cqrs.query.paged.Paging;
import de.kkendzia.myintranet.app._framework.result.ListResult;
import de.kkendzia.myintranet.app.useractions.queries.FindFavoriteActions;
import de.kkendzia.myintranet.app.useractions.queries.FindFavoriteActions.Failure;
import de.kkendzia.myintranet.app.useractions.queries.FindFavoriteActions.FindFavoriteActionsHandler;
import de.kkendzia.myintranet.app.useractions.shared.ActionItem;
import de.kkendzia.myintranet.domain.user.EIUser;
import de.kkendzia.myintranet.domain.user.EIUserAction;
import de.kkendzia.myintranet.microstream._framework.AbstractPagedMSQueryHandler;
import one.microstream.storage.types.StorageManager;
import org.springframework.stereotype.Component;

import static java.util.Comparator.comparing;

@Component
public class FindFavoriteActionsMSHandler
        extends AbstractPagedMSQueryHandler<EIUserAction>
        implements FindFavoriteActionsHandler
{
    public FindFavoriteActionsMSHandler(final StorageManager storageManager)
    {
        super(storageManager);
        registerSortOrder(EIUserAction.PROPERTY_TIMESTAMP, comparing(EIUserAction::getTimestamp));
    }

    @Override
    public ListResult<ActionItem, Failure> fetchAll(
            final FindFavoriteActions query,
            final Paging paging)
    {
        final EIUser user = getRoot().getEiUsers().get(query.userId());

        final var result =
                applyPaging(user.getFavoriteActions().stream(), paging)
                        .map(i -> new ActionItem(i.getTitle(), i.getRoute(), i.getTimestamp()))
                        .toList();
        return ListResult.success(result);
    }
}
