package de.kkendzia.myintranet.microstream.queries.mandant;

import de.kkendzia.myintranet.app._shared.FindByIDFailure;
import de.kkendzia.myintranet.app.mandant.queries.FindMandantByID;
import de.kkendzia.myintranet.domain.mandant.Mandant;
import de.kkendzia.myintranet.microstream._core.MyIntranetRoot;
import de.kkendzia.myintranet.microstream._framework.AbstractMSQueryHandler;
import one.microstream.storage.types.StorageManager;
import org.springframework.stereotype.Component;

import static de.kkendzia.myintranet.app._framework.cqrs.QueryHandler.ListQueryResult.success;

@Component
public class FindMandantByIDMSHandler extends AbstractMSQueryHandler implements FindMandantByID.FindMandantByIDHandler
{
    public FindMandantByIDMSHandler(
            final MyIntranetRoot root,
            final StorageManager storageManager)
    {
        super(root, storageManager);
    }

    @Override
    public ListQueryResult<Mandant, FindByIDFailure> fetchAll(final FindMandantByID query)
    {
        return success(getRoot().getMandanten().get(query.id()));
    }
}
