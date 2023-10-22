package de.kkendzia.myintranet.domain.user;

import de.kkendzia.myintranet.domain._framework.dao.CRUDDAO;

import java.util.Optional;

public interface PermissionDAO extends CRUDDAO<Permission, Long>
{
    Optional<Permission> findOptionalByName(String name);
    default Permission findByName(String name)
    {
        return findOptionalByName(name).orElse(null);
    }
}
