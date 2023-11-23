package de.kkendzia.myintranet.app.search.queries;

import de.kkendzia.myintranet.app._framework.cqrs.query.QueryHandler;
import de.kkendzia.myintranet.app._framework.cqrs.query.paged.PagedQuery;
import de.kkendzia.myintranet.domain.user.EIUser.EIUserID;

public record SearchEIUsers(String searchtext) implements PagedQuery<SearchEIUsers.ResultItem, SearchEIUsers.Failure>
{
    interface SearchEIUserHandler extends QueryHandler<SearchEIUsers, SearchEIUsers.ResultItem, SearchEIUsers.Failure>
    {
        @Override
        default Class<SearchEIUsers> getQueryClass()
        {
            return SearchEIUsers.class;
        }
    }

    public record ResultItem(
            EIUserID id,
            String name)
    {
        public String idString()
        {
            return id().toString();
        }
    }

    public enum Failure
    {
        UNKNOWN
    }
}
