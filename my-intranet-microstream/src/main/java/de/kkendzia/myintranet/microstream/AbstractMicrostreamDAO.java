package de.kkendzia.myintranet.microstream;

import de.kkendzia.myintranet.domain._framework.CRUDDAO;
import one.microstream.storage.types.StorageManager;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractMicrostreamDAO<T, I> implements CRUDDAO<T, I>
{
    // TODO
    @Autowired
    private MyIntranetRoot root;
    @Autowired
    private StorageManager manager;

    public MyIntranetRoot getRoot()
    {
        return root;
    }

    public StorageManager getManager()
    {
        return manager;
    }
}
