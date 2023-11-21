package de.kkendzia.myintranet.app.user.queries.favoriteactions;

import de.kkendzia.myintranet.app._framework.cqrs.QueryHandler;
import de.kkendzia.myintranet.app.user.shared.ActionItem;
import de.kkendzia.myintranet.domain._core.repository.AggregateLookup;
import de.kkendzia.myintranet.domain.user.EIUser;
import de.kkendzia.myintranet.domain.user.EIUser.EIUserID;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Objects.requireNonNull;

@Service
public class FavoriteActionsQueryHandler implements QueryHandler<FavoriteActionsQuery, List<ActionItem>>
{
    private AggregateLookup<EIUser, EIUserID> lookup;

    public FavoriteActionsQueryHandler(final AggregateLookup<EIUser, EIUserID> lookup)
    {
        this.lookup = requireNonNull(lookup, "lookup can't be null!");
    }

    @Override
    public List<ActionItem> execute(final FavoriteActionsQuery query)
    {
        return lookup.getByID(query.userId())
                .getFavoriteActions()
                .stream()
                .limit(query.limit())
                .map(x -> new ActionItem(x.getTitle(), x.getRoute()))
                .toList();
    }
}
