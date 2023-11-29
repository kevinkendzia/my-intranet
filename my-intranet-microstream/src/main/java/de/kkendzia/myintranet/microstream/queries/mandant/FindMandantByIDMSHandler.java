package de.kkendzia.myintranet.microstream.queries.mandant;

import de.kkendzia.myintranet.app._framework.result.ListResult;
import de.kkendzia.myintranet.app._shared.FindByIDFailure;
import de.kkendzia.myintranet.app.mandant._shared.MandantSheet;
import de.kkendzia.myintranet.app.mandant.queries.GetMandantSheetByID;
import de.kkendzia.myintranet.microstream._framework.AbstractMSQueryHandler;
import one.microstream.storage.types.StorageManager;
import org.springframework.stereotype.Component;

@Component
public class FindMandantByIDMSHandler extends AbstractMSQueryHandler
        implements GetMandantSheetByID.GetEditSheetByIDHandler
{
    public FindMandantByIDMSHandler(
            final StorageManager storageManager)
    {
        super(storageManager);
    }

    @Override
    public ListResult<MandantSheet, FindByIDFailure> fetchAll(final GetMandantSheetByID query)
    {
        final var mandant = getRoot().getMandanten().get(query.id());
        if (mandant == null)
        {
            return ListResult.failure(FindByIDFailure.NOT_EXIST);
        }
        return ListResult.success(new MandantSheet(mandant));
    }
}
