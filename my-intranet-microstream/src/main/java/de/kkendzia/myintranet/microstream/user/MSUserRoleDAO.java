package de.kkendzia.myintranet.microstream.user;

import de.kkendzia.myintranet.domain.user.UserRole;
import de.kkendzia.myintranet.domain.user.UserRoleDAO;
import de.kkendzia.myintranet.microstream.MyIntranetRoot;
import de.kkendzia.myintranet.microstream._framework.AbstractMSDAO;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Stream;

@Component
public class MSUserRoleDAO extends AbstractMSDAO<UserRole, Long> implements UserRoleDAO
{
    public MSUserRoleDAO()
    {
        super(MyIntranetRoot::getUserRoles);
    }

    @Override
    public Stream<UserRole> findAllByUserId(long id)
    {
        return getRoot().getUserRoles().stream().filter(ur -> Objects.equals(ur.getUserId(), id));
    }
}
