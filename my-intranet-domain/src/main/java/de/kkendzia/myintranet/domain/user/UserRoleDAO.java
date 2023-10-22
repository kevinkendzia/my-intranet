package de.kkendzia.myintranet.domain.user;

import de.kkendzia.myintranet.domain._framework.dao.CRUDDAO;

import java.util.stream.Stream;

public interface UserRoleDAO extends CRUDDAO<UserRole, Long>
{
    Stream<UserRole> findAllByUserId(long id);
}
