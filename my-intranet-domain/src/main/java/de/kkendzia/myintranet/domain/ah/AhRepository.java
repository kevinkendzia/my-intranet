package de.kkendzia.myintranet.domain.ah;

import de.kkendzia.myintranet.domain._core.repository.AggregateLookup;
import de.kkendzia.myintranet.domain._core.repository.AssociationResolver;
import de.kkendzia.myintranet.domain._core.repository.Repository;

// 29.10.2023 KK TODO: Change to Repository? github.com/VaughnVernon/IDDD_Samples/
public interface AhRepository
        extends
        Repository<Ah, Ah.AhID>,
        AggregateLookup<Ah, Ah.AhID>,
        AssociationResolver<Ah, Ah.AhID>
{
}
