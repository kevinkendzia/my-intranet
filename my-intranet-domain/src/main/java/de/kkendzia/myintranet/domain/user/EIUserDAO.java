package de.kkendzia.myintranet.domain.user;

import de.kkendzia.myintranet.domain._framework.dao.CRUDDAO;
import de.kkendzia.myintranet.domain._framework.dao.Paging;

import java.util.Optional;
import java.util.stream.Stream;

@Deprecated
public interface EIUserDAO extends CRUDDAO<EIUser, Long>
{
    Optional<EIUser> findByUsername(String userName);

    long countAllByUserNameLike(String searchText);

    Stream<EIUser> findAllByUserNameLike(String searchText, Paging paging);
}
