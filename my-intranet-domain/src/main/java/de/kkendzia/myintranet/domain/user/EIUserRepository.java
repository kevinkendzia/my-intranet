package de.kkendzia.myintranet.domain.user;

import de.kkendzia.myintranet.domain._core.repository.AggregateLookup;
import de.kkendzia.myintranet.domain._core.repository.AssociationResolver;
import de.kkendzia.myintranet.domain.user.EIUser.EIUserID;

public interface EIUserRepository
        extends
        AggregateLookup<EIUser, EIUserID>,
        AssociationResolver<EIUser, EIUserID>
{
}
