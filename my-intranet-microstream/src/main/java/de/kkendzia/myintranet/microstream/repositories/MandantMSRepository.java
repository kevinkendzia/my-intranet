package de.kkendzia.myintranet.microstream.repositories;

import de.kkendzia.myintranet.domain.mandant.Mandant;
import de.kkendzia.myintranet.domain.mandant.MandantRepository;
import de.kkendzia.myintranet.microstream._framework.AbstractMSRepository;
import one.microstream.storage.types.StorageManager;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class MandantMSRepository extends AbstractMSRepository<Mandant, Mandant.MandantID> implements MandantRepository
{
    public MandantMSRepository(final StorageManager storageManager)
    {
        super(storageManager);
    }

    @Override
    protected Map<Mandant.MandantID, Mandant> getRootCollection()
    {
        return getRoot().getMandanten();
    }
}
