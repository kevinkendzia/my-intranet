package de.kkendzia.myintranet.microstream._framework;

import de.kkendzia.myintranet.microstream._core.MyIntranetRoot;
import one.microstream.storage.types.StorageManager;

import static java.util.Objects.requireNonNull;

public abstract class AbstractMSQueryHandler
{
    private final MyIntranetRoot root;
    private final StorageManager storageManager;

    protected AbstractMSQueryHandler(final MyIntranetRoot root, final StorageManager storageManager)
    {
        this.root = requireNonNull(root, "root can't be null!");
        this.storageManager = requireNonNull(storageManager, "storageManager can't be null!");
    }

    protected MyIntranetRoot getRoot()
    {
        return root;
    }

    protected StorageManager getStorageManager()
    {
        return storageManager;
    }

}
