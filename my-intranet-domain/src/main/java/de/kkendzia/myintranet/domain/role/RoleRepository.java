package de.kkendzia.myintranet.domain.role;

import de.kkendzia.myintranet.domain._core.repository.AggregateLookup;
import de.kkendzia.myintranet.domain._core.repository.AssociationResolver;

public interface RoleRepository extends AggregateLookup<Role, Role.RoleID>, AssociationResolver<Role, Role.RoleID>
{
}
