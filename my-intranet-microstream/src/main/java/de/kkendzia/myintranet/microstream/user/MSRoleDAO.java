package de.kkendzia.myintranet.microstream.user;

import de.kkendzia.myintranet.domain._framework.dao.Paging;
import de.kkendzia.myintranet.domain.role.Role;
import de.kkendzia.myintranet.domain.role.RoleRepository;
import de.kkendzia.myintranet.microstream.MyIntranetRoot;
import de.kkendzia.myintranet.microstream._framework.AbstractMSDAO;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
public class MSRoleDAO extends AbstractMSDAO<Role, Long> implements RoleRepository
{
    public MSRoleDAO()
    {
        super(MyIntranetRoot::getRoles);
    }

    @Override
    public long countAllByNameLike(String searchText)
    {
        return findAll().filter(p -> p.getName().toLowerCase().contains(searchText.toLowerCase())).count();
    }

    @Override
    public Stream<Role> findAllByNameLike(String searchText, Paging paging)
    {
        return findAll()
                .filter(p -> p.getName().toLowerCase().contains(searchText.toLowerCase()))
                .skip(paging.offset())
                .limit(paging.limit());
    }
}
