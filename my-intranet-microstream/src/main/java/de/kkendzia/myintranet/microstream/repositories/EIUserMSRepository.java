package de.kkendzia.myintranet.microstream.repositories;

import de.kkendzia.myintranet.domain.user.EIUser;
import de.kkendzia.myintranet.domain.user.EIUser.EIUserID;
import de.kkendzia.myintranet.domain.user.EIUserRepository;
import de.kkendzia.myintranet.microstream._framework.AbstractMSRepository;
import org.eclipse.store.storage.types.StorageManager;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class EIUserMSRepository extends AbstractMSRepository<EIUser, EIUserID> implements EIUserRepository
{
    public EIUserMSRepository(final StorageManager storageManager)
    {
        super(storageManager);
    }

    @Override
    protected Map<EIUserID, EIUser> getRootCollection()
    {
        return getRoot().getEiUsers();
    }

    @Override
    protected void storeAssociations(final EIUser entity)
    {
        getStorageManager().store(entity.getRoles().ids());
        getStorageManager().store(entity.getFavoriteActions());
        getStorageManager().store(entity.getRecentActions());
    }
}
