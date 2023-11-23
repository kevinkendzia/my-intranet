package de.kkendzia.myintranet.app.useractions.queries;

import de.kkendzia.myintranet.app._framework.cqrs.query.QueryHandler;
import de.kkendzia.myintranet.app._framework.cqrs.query.paged.PagedQuery;
import de.kkendzia.myintranet.app.useractions.shared.ActionItem;
import de.kkendzia.myintranet.domain.user.EIUser.EIUserID;

public record FindRecentActions(EIUserID userId) implements PagedQuery<ActionItem, FindRecentActions.Failure>
{
    interface FindRecentActionsHandler extends QueryHandler<FindRecentActions, ActionItem, Failure>
    {
        @Override
        default Class<FindRecentActions> getQueryClass()
        {
            return FindRecentActions.class;
        }
    }

    public enum Failure
    {
        UNKNOWN
    }
}
