package de.kkendzia.myintranet.microstream.queries.search;

import de.kkendzia.myintranet.app._framework.cqrs.query.paged.Paging;
import de.kkendzia.myintranet.app._framework.result.ListResult;
import de.kkendzia.myintranet.app._shared.failures.SearchFailure;
import de.kkendzia.myintranet.app.search.queries.SearchPermissions;
import de.kkendzia.myintranet.app.search.queries.SearchPermissions.ResultItem;
import de.kkendzia.myintranet.app.search.queries.SearchPermissions.SearchPermissionsHandler;
import de.kkendzia.myintranet.domain.permission.Permission;
import de.kkendzia.myintranet.microstream._framework.AbstractMSQueryHandler;
import org.eclipse.store.storage.types.StorageManager;
import org.springframework.stereotype.Component;

@Component
public class MSSearchPermissionsHandler extends AbstractMSQueryHandler<Permission> implements SearchPermissionsHandler
{
    public MSSearchPermissionsHandler(final StorageManager storageManager)
    {
        super(storageManager);
    }

    @Override
    public ListResult<ResultItem, SearchFailure> fetchAll(final SearchPermissions query, final Paging paging)
    {
        final var permissions = getRoot()
                .getPermissions()
                .values()
                .stream()
                .filter(u -> u.getName().toLowerCase().contains(query.searchtext().toLowerCase()));

        return ListResult.success(
                applyPaging(permissions, paging)
                        .map(u -> new ResultItem(u.getId(), u.getName()))
                        .toList());
    }
}
