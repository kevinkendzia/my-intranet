package de.kkendzia.myintranet.domain.user;

import de.kkendzia.myintranet.domain._framework.dao.CRUDDAO;
import de.kkendzia.myintranet.domain._framework.dao.Paging;

import java.util.Optional;
import java.util.stream.Stream;

public interface PermissionDAO extends CRUDDAO<Permission, Long>
{
    Optional<Permission> findOptionalByName(String name);
    default Permission findByName(String name)
    {
        return findOptionalByName(name).orElse(null);
    }

    long countAllByNameLike(String searchText);
    Stream<Permission> findAllByNameLike(String searchText, Paging paging);
}
