package de.kkendzia.myintranet.domain.mandant;

import de.kkendzia.myintranet.domain._core.repository.AggregateLookup;
import de.kkendzia.myintranet.domain._core.repository.AssociationResolver;

public interface MandantRepository
        extends
        AggregateLookup<Mandant, Mandant.MandantID>,
        AssociationResolver<Mandant, Mandant.MandantID>
{
}
