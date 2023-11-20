package de.kkendzia.myintranet.domain.user.auth;

import de.kkendzia.myintranet.domain._framework.dao.CRUDDAO;
import de.kkendzia.myintranet.domain._framework.dao.Paging;

import java.util.stream.Stream;

public interface RoleDAO extends CRUDDAO<Role, Long>
{
    long countAllByNameLike(String searchText);

    Stream<Role> findAllByNameLike(String searchText, Paging paging);
}
