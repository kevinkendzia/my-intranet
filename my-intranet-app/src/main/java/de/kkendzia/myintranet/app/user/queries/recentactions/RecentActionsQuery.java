package de.kkendzia.myintranet.app.user.queries.recentactions;

import de.kkendzia.myintranet.app._framework.cqrs.QueryHandler;
import de.kkendzia.myintranet.app.user.shared.ActionItem;
import de.kkendzia.myintranet.domain.user.EIUser;

import java.util.List;

public record RecentActionsQuery(EIUser.EIUserID userId, int limit) implements QueryHandler.Query<List<ActionItem>>
{
}
