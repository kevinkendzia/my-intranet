package de.kkendzia.myintranet.microstream;

import de.kkendzia.myintranet.domain.role.Role;
import de.kkendzia.myintranet.domain.role.RoleRepository;
import de.kkendzia.myintranet.microstream._core.MyIntranetRoot;
import de.kkendzia.myintranet.microstream._framework.AbstractMSRepository;
import one.microstream.storage.types.StorageManager;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoleMSRepository extends AbstractMSRepository<Role, Role.RoleID> implements RoleRepository
{
    public RoleMSRepository(final MyIntranetRoot root, final StorageManager storageManager)
    {
        super(root, storageManager);
    }

    @Override
    protected List<Role> getRootCollection()
    {
        return getRoot().getRoles();
    }
}
