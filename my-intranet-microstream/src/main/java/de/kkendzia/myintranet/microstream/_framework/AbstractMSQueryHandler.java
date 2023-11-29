package de.kkendzia.myintranet.microstream._framework;

import one.microstream.storage.types.StorageManager;

import static java.util.Objects.requireNonNull;

public abstract class AbstractMSQueryHandler implements HasStorageManager
{
    private final StorageManager storageManager;

    protected AbstractMSQueryHandler(final StorageManager storageManager)
    {
        this.storageManager = requireNonNull(storageManager, "storageManager can't be null!");
    }
    
    public StorageManager getStorageManager()
    {
        return storageManager;
    }
}
