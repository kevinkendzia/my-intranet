package de.kkendzia.myintranet.microstream.user;

import de.kkendzia.myintranet.domain.user.auth.RolePermission;
import de.kkendzia.myintranet.domain.user.auth.RolePermissionDAO;
import de.kkendzia.myintranet.microstream.MyIntranetRoot;
import de.kkendzia.myintranet.microstream._framework.AbstractMSDAO;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Stream;

@Component
public class MSRolePermissionDAO extends AbstractMSDAO<RolePermission, Long> implements RolePermissionDAO
{
    public MSRolePermissionDAO()
    {
        super(MyIntranetRoot::getRolePermissions);
    }

    @Override
    public Stream<RolePermission> findAllByRoleId(long id)
    {
        return getRoot().getRolePermissions().stream().filter(rp -> Objects.equals(rp.getRoleId(), id));
    }
}
