package de.kkendzia.myintranet.microstream.user;

import de.kkendzia.myintranet.domain._framework.dao.Paging;
import de.kkendzia.myintranet.domain.permission.Permission;
import de.kkendzia.myintranet.domain.permission.PermissionRepository;
import de.kkendzia.myintranet.microstream.MyIntranetRoot;
import de.kkendzia.myintranet.microstream._framework.AbstractMSDAO;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import static de.kkendzia.myintranet.domain._utils.Reduce.toOnlyElement;

@Component
public class MSPermissionDAO extends AbstractMSDAO<Permission, Long> implements PermissionRepository
{
    public MSPermissionDAO()
    {
        super(MyIntranetRoot::getPermissions);
    }

    @Override
    public Optional<Permission> findOptionalByName(String name)
    {
        return getRoot().getPermissions()
                .stream()
                .filter(p -> Objects.equals(p.getName(), name))
                .reduce(toOnlyElement());
    }

    @Override
    public long countAllByNameLike(String searchText)
    {
        return findAll().filter(p -> p.getName().toLowerCase().contains(searchText.toLowerCase())).count();
    }

    @Override
    public Stream<Permission> findAllByNameLike(String searchText, Paging paging)
    {
        return findAll()
                .filter(p -> p.getName().toLowerCase().contains(searchText.toLowerCase()))
                .skip(paging.offset())
                .limit(paging.limit());
    }
}
