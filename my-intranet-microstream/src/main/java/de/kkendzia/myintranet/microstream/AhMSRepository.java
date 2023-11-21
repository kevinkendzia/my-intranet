package de.kkendzia.myintranet.microstream;

import de.kkendzia.myintranet.domain.ah.Ah;
import de.kkendzia.myintranet.domain.ah.AhRepository;
import de.kkendzia.myintranet.microstream._core.MyIntranetRoot;
import de.kkendzia.myintranet.microstream._framework.AbstractMSRepository;
import one.microstream.storage.types.StorageManager;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AhMSRepository extends AbstractMSRepository<Ah, Ah.AhID> implements AhRepository
{
    public AhMSRepository(
            final MyIntranetRoot root,
            final StorageManager storageManager)
    {
        super(root, storageManager);
    }

    @Override
    protected List<Ah> getRootCollection()
    {
        return getRoot().getAhs();
    }
}
