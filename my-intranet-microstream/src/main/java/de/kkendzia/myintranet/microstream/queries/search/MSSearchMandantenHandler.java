package de.kkendzia.myintranet.microstream.queries.search;

import de.kkendzia.myintranet.app._framework.cqrs.query.paged.Paging;
import de.kkendzia.myintranet.app._framework.result.ListResult;
import de.kkendzia.myintranet.app._shared.failures.SearchFailure;
import de.kkendzia.myintranet.app.search.queries.SearchMandanten;
import de.kkendzia.myintranet.app.search.queries.SearchMandanten.ResultItem;
import de.kkendzia.myintranet.app.search.queries.SearchMandanten.SearchMandantenHandler;
import de.kkendzia.myintranet.domain.mandant.Mandant;
import de.kkendzia.myintranet.microstream._framework.AbstractMSQueryHandler;
import org.eclipse.store.storage.types.StorageManager;
import org.springframework.stereotype.Component;

@Component
public class MSSearchMandantenHandler extends AbstractMSQueryHandler<Mandant> implements SearchMandantenHandler
{
    public MSSearchMandantenHandler(final StorageManager storageManager)
    {
        super(storageManager);
    }

    @Override
    public ListResult<ResultItem, SearchFailure> fetchAll(final SearchMandanten query, final Paging paging)
    {
        final var mandanten = getRoot()
                .getMandanten()
                .values()
                .stream()
                .filter(u -> u.getName().toLowerCase().contains(query.searchtext().toLowerCase()));

        return ListResult.success(
                applyPaging(mandanten, paging)
                        .map(u -> new ResultItem(u.getId(), u.getName()))
                        .toList());
    }
}
