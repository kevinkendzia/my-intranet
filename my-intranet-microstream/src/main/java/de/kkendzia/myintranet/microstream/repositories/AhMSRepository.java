package de.kkendzia.myintranet.microstream.repositories;

import de.kkendzia.myintranet.domain.ah.Ah;
import de.kkendzia.myintranet.domain.ah.Ah.AhID;
import de.kkendzia.myintranet.domain.ah.AhRepository;
import de.kkendzia.myintranet.microstream._framework.AbstractMSRepository;
import org.eclipse.store.storage.types.StorageManager;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class AhMSRepository extends AbstractMSRepository<Ah, AhID> implements AhRepository
{
    public AhMSRepository(
            final StorageManager storageManager)
    {
        super(storageManager);
    }

    @Override
    protected Map<AhID, Ah> getRootCollection()
    {
        return getRoot().getAhs();
    }
}
