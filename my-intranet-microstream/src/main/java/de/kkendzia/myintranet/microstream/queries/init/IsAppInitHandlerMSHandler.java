package de.kkendzia.myintranet.microstream.queries.init;

import de.kkendzia.myintranet.app._framework.result.ListResult;
import de.kkendzia.myintranet.app.init.queries.IsAppInit;
import de.kkendzia.myintranet.microstream._framework.AbstractMSQueryHandler;
import one.microstream.storage.types.StorageManager;
import org.springframework.stereotype.Component;

@Component
public class IsAppInitHandlerMSHandler extends AbstractMSQueryHandler implements IsAppInit.IsAppInitHandler
{
    public IsAppInitHandlerMSHandler(final StorageManager storageManager)
    {
        super(storageManager);
    }

    @Override
    public ListResult<Boolean, Void> fetchAll(final IsAppInit query)
    {
        return ListResult.success(getRoot().isInit());
    }
}
