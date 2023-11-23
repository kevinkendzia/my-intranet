package de.kkendzia.myintranet.app.useractions.queries;

import de.kkendzia.myintranet.app._framework.cqrs.QueryHandler;
import de.kkendzia.myintranet.app._framework.cqrs.QueryHandler.PagedQuery;
import de.kkendzia.myintranet.app._framework.cqrs.QueryHandler.Query;
import de.kkendzia.myintranet.app.useractions.shared.ActionItem;
import de.kkendzia.myintranet.domain.user.EIUser;

import java.util.List;

import static java.util.Objects.requireNonNull;

public record FindFavoriteActions(EIUser.EIUserID userId) implements PagedQuery<ActionItem, FindFavoriteActions.Failure>
{
    interface FindFavoriteActionsHandler extends QueryHandler<FindFavoriteActions, ActionItem, Failure>
    {
        @Override
        default Class<FindFavoriteActions> getQueryClass()
        {
            return FindFavoriteActions.class;
        }
    }

    public enum Failure
    {
        UNKNOWN
    }
}
