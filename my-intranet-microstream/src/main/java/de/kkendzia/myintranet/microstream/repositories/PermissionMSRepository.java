package de.kkendzia.myintranet.microstream.repositories;

import de.kkendzia.myintranet.domain.permission.Permission;
import de.kkendzia.myintranet.domain.permission.PermissionRepository;
import de.kkendzia.myintranet.microstream._framework.AbstractMSRepository;
import org.eclipse.store.storage.types.StorageManager;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class PermissionMSRepository extends AbstractMSRepository<Permission, Permission.PermissionID>
        implements PermissionRepository
{
    public PermissionMSRepository(
            final StorageManager storageManager)
    {
        super(storageManager);
    }

    @Override
    protected Map<Permission.PermissionID, Permission> getRootCollection()
    {
        return getRoot().getPermissions();
    }
}
