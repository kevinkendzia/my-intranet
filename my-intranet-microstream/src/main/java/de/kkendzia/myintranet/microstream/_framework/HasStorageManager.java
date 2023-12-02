package de.kkendzia.myintranet.microstream._framework;

import de.kkendzia.myintranet.microstream._core.MyIntranetRoot;
import org.eclipse.store.storage.types.StorageManager;

public interface HasStorageManager
{
    StorageManager getStorageManager();

    default MyIntranetRoot getRoot()
    {
        return (MyIntranetRoot) getStorageManager().root();
    }
}
