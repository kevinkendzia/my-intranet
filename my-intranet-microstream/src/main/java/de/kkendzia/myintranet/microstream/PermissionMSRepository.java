package de.kkendzia.myintranet.microstream;

import de.kkendzia.myintranet.domain.permission.Permission;
import de.kkendzia.myintranet.domain.permission.PermissionRepository;
import de.kkendzia.myintranet.microstream._core.MyIntranetRoot;
import de.kkendzia.myintranet.microstream._framework.AbstractMSRepository;
import one.microstream.storage.types.StorageManager;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PermissionMSRepository extends AbstractMSRepository<Permission, Permission.PermissionID>
        implements PermissionRepository
{
    public PermissionMSRepository(
            final MyIntranetRoot root,
            final StorageManager storageManager)
    {
        super(root, storageManager);
    }

    @Override
    protected List<Permission> getRootCollection()
    {
        return getRoot().getPermissions();
    }
}
