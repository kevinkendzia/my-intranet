package de.kkendzia.myintranet.microstream.user;

import de.kkendzia.myintranet.domain.user.Permission;
import de.kkendzia.myintranet.domain.user.PermissionDAO;
import de.kkendzia.myintranet.microstream.MyIntranetRoot;
import de.kkendzia.myintranet.microstream._framework.AbstractMSDAO;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

import static de.kkendzia.myintranet.domain._framework.utils.Reduce.toOnlyElement;

@Component
public class MSPermissionDAO extends AbstractMSDAO<Permission, Long> implements PermissionDAO
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
}
