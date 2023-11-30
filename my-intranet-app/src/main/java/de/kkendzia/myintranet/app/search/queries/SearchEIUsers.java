package de.kkendzia.myintranet.app.search.queries;

import de.kkendzia.myintranet.app._framework.cqrs.query.paged.PagedQuery;
import de.kkendzia.myintranet.app._shared.failures.SearchFailure;
import de.kkendzia.myintranet.domain.user.EIUser.EIUserID;

public record SearchEIUsers(String searchtext) implements PagedQuery<SearchEIUsers.ResultItem, SearchFailure>
{
    public interface SearchEIUserHandler
            extends PagedQueryHandler<SearchEIUsers, SearchEIUsers.ResultItem, SearchFailure>
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
}
