package de.kkendzia.myintranet.microstream.queries.search;

import de.kkendzia.myintranet.app._framework.cqrs.query.paged.Paging;
import de.kkendzia.myintranet.app._framework.result.ListResult;
import de.kkendzia.myintranet.app._shared.failures.SearchFailure;
import de.kkendzia.myintranet.app.search.queries.SearchAhs;
import de.kkendzia.myintranet.app.search.queries.SearchAhs.ResultItem;
import de.kkendzia.myintranet.app.search.queries.SearchAhs.SearchAhsHandler;
import de.kkendzia.myintranet.domain.ah.Ah;
import de.kkendzia.myintranet.microstream._framework.AbstractMSQueryHandler;
import one.microstream.storage.types.StorageManager;
import org.springframework.stereotype.Component;

@Component
public class MSSearchAhsHandler extends AbstractMSQueryHandler<Ah> implements SearchAhsHandler
{
    public MSSearchAhsHandler(final StorageManager storageManager)
    {
        super(storageManager);
    }

    @Override
    public ListResult<ResultItem, SearchFailure> fetchAll(final SearchAhs query, final Paging paging)
    {
        final var ahs = getRoot()
                .getAhs()
                .values()
                .stream()
                .filter(u -> u.getMatchcode().toLowerCase().contains(query.searchtext().toLowerCase()));

        return ListResult.success(
                applyPaging(ahs, paging)
                        .map(u -> new ResultItem(
                                u.getId(),
                                u.getAhnr(),
                                u.getMatchcode(),
                                u.getEnterDate(),
                                u.getExitDate()))
                        .toList());
    }
}
