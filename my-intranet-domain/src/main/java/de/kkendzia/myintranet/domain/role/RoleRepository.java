package de.kkendzia.myintranet.domain.role;

import de.kkendzia.myintranet.domain._core.AggregateRoot;
import de.kkendzia.myintranet.domain._core.repository.AggregateLookup;
import de.kkendzia.myintranet.domain._core.repository.AssociationResolver;
import de.kkendzia.myintranet.domain._framework.dao.CRUDDAO;
import de.kkendzia.myintranet.domain._framework.dao.Paging;

import java.util.stream.Stream;

public interface RoleRepository extends AggregateLookup<Role, Role.RoleID>, AssociationResolver<Role, Role.RoleID>
{
    long countAllByNameLike(String searchText);

    Stream<Role> findAllByNameLike(String searchText, Paging paging);
}
