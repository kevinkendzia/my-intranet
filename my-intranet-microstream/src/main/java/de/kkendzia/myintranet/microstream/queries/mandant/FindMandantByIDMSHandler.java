package de.kkendzia.myintranet.microstream.queries.mandant;

import de.kkendzia.myintranet.app._framework.result.SingleResult;
import de.kkendzia.myintranet.app._shared.failures.FindByIDFailure;
import de.kkendzia.myintranet.app.mandant._shared.MandantSheet;
import de.kkendzia.myintranet.app.mandant.queries.GetMandantSheetByID;
import de.kkendzia.myintranet.microstream._framework.AbstractMSQueryHandler;
import org.eclipse.store.storage.types.StorageManager;
import org.springframework.stereotype.Component;

import static de.kkendzia.myintranet.app._framework.result.SingleResult.failure;
import static de.kkendzia.myintranet.app._framework.result.SingleResult.success;

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
    public SingleResult<MandantSheet, FindByIDFailure> fetchOne(final GetMandantSheetByID query)
    {
        final var mandant = getRoot().getMandanten().get(query.id());
        if (mandant == null)
        {
            return failure(FindByIDFailure.NOT_EXIST);
        }
        return success(new MandantSheet(mandant));
    }
}
