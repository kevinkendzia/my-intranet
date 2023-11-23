package de.kkendzia.myintranet.microstream.queries.mandant;

import de.kkendzia.myintranet.app._framework.result.ListResult;
import de.kkendzia.myintranet.app._shared.FindByIDFailure;
import de.kkendzia.myintranet.app.mandant.queries.FindMandantByID;
import de.kkendzia.myintranet.domain.mandant.Mandant;
import de.kkendzia.myintranet.microstream._core.MyIntranetRoot;
import de.kkendzia.myintranet.microstream._framework.AbstractMSQueryHandler;
import one.microstream.storage.types.StorageManager;
import org.springframework.stereotype.Component;

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
    public ListResult<Mandant, FindByIDFailure> fetchAll(final FindMandantByID query)
    {
        return ListResult.success(getRoot().getMandanten().get(query.id()));
    }
}
