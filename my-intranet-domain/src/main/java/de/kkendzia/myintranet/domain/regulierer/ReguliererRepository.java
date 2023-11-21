package de.kkendzia.myintranet.domain.regulierer;

import de.kkendzia.myintranet.domain._core.repository.AggregateLookup;
import de.kkendzia.myintranet.domain._core.repository.AssociationResolver;

public interface ReguliererRepository
        extends
        AggregateLookup<Regulierer, Regulierer.ReguliererID>,
        AssociationResolver<Regulierer, Regulierer.ReguliererID>
{
}
