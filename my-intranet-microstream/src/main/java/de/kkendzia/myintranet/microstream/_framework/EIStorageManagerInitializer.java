package de.kkendzia.myintranet.microstream._framework;

import de.kkendzia.myintranet.domain.user.EIUser;
import de.kkendzia.myintranet.microstream.MyIntranetRoot;
import one.microstream.integrations.spring.boot.types.config.StorageManagerInitializer;
import one.microstream.storage.types.StorageManager;
import org.springframework.stereotype.Component;

@Component
public class EIStorageManagerInitializer implements StorageManagerInitializer
{
    @Override
    public void initialize(StorageManager storageManager)
    {
        MyIntranetRoot root = (MyIntranetRoot) storageManager.root();
        if(!root.isInit())
        {
            EIUser u = new EIUser();
            u.setId(1);
            u.setUserName("root");
            u.setPassword("root");
            root.getEIUsers().add(u);
            storageManager.store(root.getEIUsers());

            root.setInit(true);
            storageManager.storeRoot();
        }
    }
}
