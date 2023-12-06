package de.kkendzia.myintranet.microstream.queries.mandant;

import de.kkendzia.myintranet.app._framework.cqrs.query.paged.Paging;
import de.kkendzia.myintranet.app._framework.result.ListResult;
import de.kkendzia.myintranet.app.mandant.queries.ListMandanten;
import de.kkendzia.myintranet.domain.mandant.Mandant;
import de.kkendzia.myintranet.microstream._framework.AbstractMSQueryHandler;
import one.microstream.storage.types.StorageManager;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

import static java.util.Comparator.comparing;

@Component
public class MSListMandantenHandler
        extends AbstractMSQueryHandler<Mandant>
        implements ListMandanten.ListMandantenHandler
{
    public MSListMandantenHandler(final StorageManager storageManager)
    {
        super(storageManager);
        registerSortOrder("name", comparing(Mandant::getLongName));
    }

    @Override
    public ListResult<ListMandanten.MandantItem, ListMandanten.Failure> fetchAll(
            final ListMandanten query,
            final Paging paging)
    {
        final Stream<Mandant> mandanten = getRoot().getMandanten().values().stream();
        final Stream<Mandant> paged = applyPaging(mandanten, paging);
        final var mapped = paged.map(m -> new ListMandanten.MandantItem(m.getId(), m.getLongName())).toList();
        return ListResult.success(mapped);
    }
}
