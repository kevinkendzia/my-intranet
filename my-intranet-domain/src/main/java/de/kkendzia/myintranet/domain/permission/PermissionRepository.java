package de.kkendzia.myintranet.domain.permission;

import de.kkendzia.myintranet.domain._core.repository.AggregateLookup;
import de.kkendzia.myintranet.domain._core.repository.AssociationResolver;

public interface PermissionRepository
        extends
        AggregateLookup<Permission, Permission.PermissionID>,
        AssociationResolver<Permission, Permission.PermissionID>
{
}
