package de.kkendzia.myintranet.microstream._framework.id;

import de.kkendzia.myintranet.microstream.MyIntranetRoot;
import one.microstream.storage.types.StorageManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DefaultIdHandler implements IdHandler
{
    @Autowired
    private MyIntranetRoot root;
    @Autowired
    private StorageManager storageManager;

    @Override
    public synchronized long getNewId()
    {
        long lastId = root.getLastId();
        long newId = lastId + 1;

        root.setLastId(newId);
        storageManager.storeRoot();

        return newId;
    }
}
