package de.kkendzia.myintranet.domain.verband;

import de.kkendzia.myintranet.domain._core.repository.AggregateLookup;
import de.kkendzia.myintranet.domain._core.repository.AssociationResolver;

public interface VerbandRepository
        extends
        AggregateLookup<Verband, Verband.VerbandID>,
        AssociationResolver<Verband, Verband.VerbandID>
{
}
