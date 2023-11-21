package de.kkendzia.myintranet.domain.user;

import de.kkendzia.myintranet.domain._core.AggregateRoot;
import de.kkendzia.myintranet.domain._core.exception.DomainException;
import de.kkendzia.myintranet.domain._core.repository.AggregateLookup;
import de.kkendzia.myintranet.domain._core.repository.AssociationResolver;
import de.kkendzia.myintranet.domain._framework.dao.CRUDDAO;
import de.kkendzia.myintranet.domain._framework.dao.Paging;
import de.kkendzia.myintranet.domain.user.EIUser.EIUserID;

import java.util.Optional;
import java.util.stream.Stream;

public interface EIUserRepository
        extends
        AggregateLookup<EIUser, EIUserID>,
        AssociationResolver<EIUser, EIUserID>
{
    Optional<EIUser> findByUsername(String userName);

    default EIUser getByUsername(String userName)
    {
        return findByUsername(userName)
                .orElseThrow(() -> new DomainException("Couldn't find user with username \"" + userName + "\"!"));
    }

    long countAllByUserNameLike(String searchText);

    Stream<EIUser> findAllByUserNameLike(String searchText, Paging paging);

    void update(EIUser user);
}
