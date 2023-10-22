package de.kkendzia.myintranet.domain.user;

import de.kkendzia.myintranet.domain._framework.dao.CRUDDAO;

import java.util.stream.Stream;

public interface RolePermissionDAO extends CRUDDAO<RolePermission, Long>
{
    Stream<RolePermission> findAllByRoleId(long id);
}
