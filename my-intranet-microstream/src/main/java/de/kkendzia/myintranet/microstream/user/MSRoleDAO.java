package de.kkendzia.myintranet.microstream.user;

import de.kkendzia.myintranet.domain.user.Role;
import de.kkendzia.myintranet.domain.user.RoleDAO;
import de.kkendzia.myintranet.microstream.MyIntranetRoot;
import de.kkendzia.myintranet.microstream._framework.AbstractMSDAO;
import org.springframework.stereotype.Component;

@Component
public class MSRoleDAO extends AbstractMSDAO<Role, Long> implements RoleDAO
{
    public MSRoleDAO()
    {
        super(MyIntranetRoot::getRoles);
    }
}
