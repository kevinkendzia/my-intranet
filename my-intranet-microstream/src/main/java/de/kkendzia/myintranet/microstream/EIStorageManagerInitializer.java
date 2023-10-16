package de.kkendzia.myintranet.microstream;

import de.kkendzia.myintranet.domain.shared.Mandant;
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
            root.getMandanten().add(new Mandant(1, "EMV"));
            root.getMandanten().add(new Mandant(2, "KKV"));
        }
    }
}
