package de.kkendzia.myintranet.app.useractions.queries;

import de.kkendzia.myintranet.app._framework.cqrs.query.paged.PagedQuery;
import de.kkendzia.myintranet.app.useractions._shared.ActionItem;
import de.kkendzia.myintranet.domain.user.EIUser.EIUserID;

public record FindFavoriteActions(EIUserID userId) implements PagedQuery<ActionItem, FindFavoriteActions.Failure>
{
    public interface FindFavoriteActionsHandler extends PagedQueryHandler<FindFavoriteActions, ActionItem, Failure>
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
