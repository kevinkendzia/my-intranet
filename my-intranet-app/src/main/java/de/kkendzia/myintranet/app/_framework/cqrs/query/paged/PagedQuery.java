package de.kkendzia.myintranet.app._framework.cqrs.query.paged;

import de.kkendzia.myintranet.app._framework.cqrs.query.QueryHandler;

public interface PagedQuery<R, F> extends QueryHandler.Query<R, F>
{
}
