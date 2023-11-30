package de.kkendzia.myintranet.microstream.queries.search;

import de.kkendzia.myintranet.app._framework.cqrs.query.paged.Paging;
import de.kkendzia.myintranet.app._framework.result.ListResult;
import de.kkendzia.myintranet.app._shared.failures.SearchFailure;
import de.kkendzia.myintranet.app.search.queries.SearchRoles;
import de.kkendzia.myintranet.app.search.queries.SearchRoles.ResultItem;
import de.kkendzia.myintranet.app.search.queries.SearchRoles.SearchRolesHandler;
import de.kkendzia.myintranet.domain.role.Role;
import de.kkendzia.myintranet.microstream._framework.AbstractMSQueryHandler;
import one.microstream.storage.types.StorageManager;
import org.springframework.stereotype.Component;

@Component
public class MSSearchRolesHandler extends AbstractMSQueryHandler<Role> implements SearchRolesHandler
{
    public MSSearchRolesHandler(final StorageManager storageManager)
    {
        super(storageManager);
    }

    @Override
    public ListResult<ResultItem, SearchFailure> fetchAll(final SearchRoles query, final Paging paging)
    {
        final var roles = getRoot()
                .getRoles()
                .values()
                .stream()
                .filter(u -> u.getName().toLowerCase().contains(query.searchtext().toLowerCase()));

        return ListResult.success(
                applyPaging(roles, paging)
                        .map(u -> new SearchRoles.ResultItem(u.getId(), u.getName()))
                        .toList());
    }
}
