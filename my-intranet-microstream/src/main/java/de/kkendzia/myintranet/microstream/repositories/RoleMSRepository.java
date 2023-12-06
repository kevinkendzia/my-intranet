package de.kkendzia.myintranet.microstream.repositories;

import de.kkendzia.myintranet.domain.role.Role;
import de.kkendzia.myintranet.domain.role.RoleRepository;
import de.kkendzia.myintranet.microstream._framework.AbstractMSRepository;
import one.microstream.storage.types.StorageManager;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class RoleMSRepository extends AbstractMSRepository<Role, Role.RoleID> implements RoleRepository
{
    public RoleMSRepository(final StorageManager storageManager)
    {
        super(storageManager);
    }

    @Override
    protected Map<Role.RoleID, Role> getRootCollection()
    {
        return getRoot().getRoles();
    }
}
