package de.kkendzia.myintranet.microstream._framework.id;

import de.kkendzia.myintranet.microstream.MyIntranetRoot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DefaultIdHandler implements IdHandler
{
    @Autowired
    private MyIntranetRoot root;

    @Override
    public synchronized long getNewId()
    {
        long lastId = root.getLastId();
        long newId = lastId + 1;
        root.setLastId(newId);
        return newId;
    }
}
