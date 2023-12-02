package de.kkendzia.myintranet.microstream._framework;

import de.kkendzia.myintranet.microstream._core.MyIntranetRoot;
import org.eclipse.store.storage.embedded.types.EmbeddedStorage;
import org.eclipse.store.storage.types.StorageManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Paths;

@Configuration
public class StorageManagerProvider
{
    @Bean(destroyMethod = "shutdown")
    public StorageManager storageManager()
    {
        return EmbeddedStorage.start(new MyIntranetRoot(), Paths.get("my-intranet-ei/db"));
    }
}
