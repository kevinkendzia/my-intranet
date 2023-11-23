package de.kkendzia.myintranet.app.useractions.queries;

import de.kkendzia.myintranet.app._framework.cqrs.query.QueryHandler;
import de.kkendzia.myintranet.app._framework.cqrs.query.paged.PagedQuery;
import de.kkendzia.myintranet.app.useractions.shared.ActionItem;
import de.kkendzia.myintranet.domain.user.EIUser;

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
