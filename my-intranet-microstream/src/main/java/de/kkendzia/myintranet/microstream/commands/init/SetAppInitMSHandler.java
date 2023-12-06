package de.kkendzia.myintranet.microstream.commands.init;

import de.kkendzia.myintranet.app._framework.result.VoidResult;
import de.kkendzia.myintranet.app.init.commands.SetAppInit;
import de.kkendzia.myintranet.microstream._core.MyIntranetRoot;
import one.microstream.storage.types.StorageManager;
import org.springframework.stereotype.Component;

import static java.util.Objects.requireNonNull;

@Component
public class SetAppInitMSHandler implements SetAppInit.SetAppInitHandler
{
    private final StorageManager storageManager;

    public SetAppInitMSHandler(final StorageManager storageManager)
    {
        this.storageManager = requireNonNull(storageManager, "storageManager can't be null!");
    }

    @Override
    public VoidResult<Void> run(final SetAppInit command)
    {
        final MyIntranetRoot root = (MyIntranetRoot) storageManager.root();
        root.setInit(command.init());
        storageManager.storeRoot();
        return VoidResult.success();
    }
}
