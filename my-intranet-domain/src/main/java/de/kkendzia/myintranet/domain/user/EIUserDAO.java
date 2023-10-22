package de.kkendzia.myintranet.domain.user;

import de.kkendzia.myintranet.domain._framework.dao.CRUDDAO;

import java.util.Optional;

public interface EIUserDAO extends CRUDDAO<EIUser, Long>
{
    Optional<EIUser> findByUsername(String userName);
}
