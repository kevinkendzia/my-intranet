package de.kkendzia.myintranet.microstream.queries.init;

import de.kkendzia.myintranet.app._framework.result.SingleResult;
import de.kkendzia.myintranet.app.init.queries.IsAppInit;
import de.kkendzia.myintranet.microstream._framework.AbstractMSQueryHandler;
import one.microstream.storage.types.StorageManager;
import org.springframework.stereotype.Component;

import static de.kkendzia.myintranet.app._framework.result.SingleResult.success;

@Component
public class IsAppInitHandlerMSHandler extends AbstractMSQueryHandler implements IsAppInit.IsAppInitHandler
{
    public IsAppInitHandlerMSHandler(final StorageManager storageManager)
    {
        super(storageManager);
    }

    @Override
    public SingleResult<Boolean, Void> fetchOne(final IsAppInit query)
    {
        return success(getRoot().isInit());
    }
}
