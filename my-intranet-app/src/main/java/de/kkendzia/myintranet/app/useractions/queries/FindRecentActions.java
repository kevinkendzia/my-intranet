package de.kkendzia.myintranet.app.useractions.queries;

import de.kkendzia.myintranet.app._framework.cqrs.QueryHandler;
import de.kkendzia.myintranet.app._framework.cqrs.QueryHandler.Query;
import de.kkendzia.myintranet.app.useractions.shared.ActionItem;
import de.kkendzia.myintranet.domain.user.EIUser.EIUserID;

import java.util.List;

import static java.util.Objects.requireNonNull;

public record FindRecentActions(EIUserID userId, int limit) implements Query<List<ActionItem>, FindRecentActions.Failure>
{
    interface FindRecentActionsHandler extends QueryHandler<FindRecentActions, List<ActionItem>, Failure>
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
