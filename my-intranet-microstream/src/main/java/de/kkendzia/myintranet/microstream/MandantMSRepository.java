package de.kkendzia.myintranet.microstream;

import de.kkendzia.myintranet.domain.mandant.Mandant;
import de.kkendzia.myintranet.domain.mandant.MandantRepository;
import de.kkendzia.myintranet.microstream._core.MyIntranetRoot;
import de.kkendzia.myintranet.microstream._framework.AbstractMSRepository;
import one.microstream.storage.types.StorageManager;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MandantMSRepository extends AbstractMSRepository<Mandant, Mandant.MandantID> implements MandantRepository
{
    public MandantMSRepository(final MyIntranetRoot root, final StorageManager storageManager)
    {
        super(root, storageManager);
    }

    @Override
    protected List<Mandant> getRootCollection()
    {
        return getRoot().getMandanten();
    }
}
