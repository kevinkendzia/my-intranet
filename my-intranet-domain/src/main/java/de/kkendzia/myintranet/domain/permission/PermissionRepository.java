package de.kkendzia.myintranet.domain.permission;

import de.kkendzia.myintranet.domain._core.repository.AggregateLookup;
import de.kkendzia.myintranet.domain._core.repository.AssociationResolver;
import de.kkendzia.myintranet.domain._framework.dao.CRUDDAO;
import de.kkendzia.myintranet.domain._framework.dao.Paging;

import java.util.Optional;
import java.util.stream.Stream;

public interface PermissionRepository
        extends
        AggregateLookup<Permission, Permission.PermissionID>,
        AssociationResolver<Permission, Permission.PermissionID>
{
    Optional<Permission> findOptionalByName(String name);

    default Permission findByName(String name)
    {
        return findOptionalByName(name).orElse(null);
    }

    long countAllByNameLike(String searchText);

    Stream<Permission> findAllByNameLike(String searchText, Paging paging);
}
