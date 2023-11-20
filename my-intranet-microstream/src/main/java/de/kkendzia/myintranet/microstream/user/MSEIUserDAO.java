package de.kkendzia.myintranet.microstream.user;

import de.kkendzia.myintranet.domain._framework.dao.Paging;
import de.kkendzia.myintranet.domain.user.EIUser;
import de.kkendzia.myintranet.domain.user.EIUserDAO;
import de.kkendzia.myintranet.microstream.MyIntranetRoot;
import de.kkendzia.myintranet.microstream._framework.AbstractMSDAO;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import static de.kkendzia.myintranet.domain._utils.Reduce.toOnlyElement;

@Component
public class MSEIUserDAO extends AbstractMSDAO<EIUser, Long> implements EIUserDAO
{
    public MSEIUserDAO()
    {
        super(MyIntranetRoot::getEIUsers);
    }

    @Override
    public Optional<EIUser> findByUsername(String userName)
    {
        return getRoot().getEIUsers()
                .stream()
                .filter(u -> Objects.equals(u.getUserName(), userName))
                .reduce(toOnlyElement());
    }

    @Override
    public long countAllByUserNameLike(String searchText)
    {
        return findAll().filter(p -> p.getUserName().toLowerCase().contains(searchText.toLowerCase())).count();
    }

    @Override
    public Stream<EIUser> findAllByUserNameLike(String searchText, Paging paging)
    {
        return findAll()
                .filter(p -> p.getUserName().toLowerCase().contains(searchText.toLowerCase()))
                .skip(paging.offset())
                .limit(paging.limit());
    }
}
