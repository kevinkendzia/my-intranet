package de.kkendzia.myintranet.microstream.queries.search;

import de.kkendzia.myintranet.app._framework.cqrs.query.paged.Paging;
import de.kkendzia.myintranet.app._framework.result.ListResult;
import de.kkendzia.myintranet.app._shared.failures.SearchFailure;
import de.kkendzia.myintranet.app.search.queries.SearchEIUsers;
import de.kkendzia.myintranet.app.search.queries.SearchEIUsers.SearchEIUserHandler;
import de.kkendzia.myintranet.domain.user.EIUser;
import de.kkendzia.myintranet.microstream._framework.AbstractMSQueryHandler;
import one.microstream.storage.types.StorageManager;
import org.springframework.stereotype.Component;

@Component
public class MSSearchEIUserHandler extends AbstractMSQueryHandler<EIUser> implements SearchEIUserHandler
{
    public MSSearchEIUserHandler(final StorageManager storageManager)
    {
        super(storageManager);
    }

    @Override
    public ListResult<SearchEIUsers.ResultItem, SearchFailure> fetchAll(final SearchEIUsers query, final Paging paging)
    {
        final var users = getRoot().getEiUsers()
                .values()
                .stream()
                .filter(u -> u.getUserName().toLowerCase().contains(query.searchtext().toLowerCase()));

        return ListResult.success(
                applyPaging(users, paging)
                        .map(u -> new SearchEIUsers.ResultItem(u.getId(), u.getUserName()))
                        .toList());
    }
}
