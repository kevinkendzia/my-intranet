package de.kkendzia.myintranet.domain._core.repository;

import de.kkendzia.myintranet.domain._core.AggregateRoot;
import de.kkendzia.myintranet.domain._core.ID;
import de.kkendzia.myintranet.domain._core.exception.DomainException;
import de.kkendzia.myintranet.domain.ah.Ah;

public interface Repository<A extends AggregateRoot<A, I>, I extends ID>
        extends
        AggregateList<Ah>,
        AggregateLookup<Ah, Ah.AhID>,
        AssociationResolver<Ah, Ah.AhID>,
        AggregateCreate<Ah, Ah.AhID>,
        AggregateUpdate<Ah, Ah.AhID>,
        AggregateDelete<Ah, Ah.AhID>
{
    //region STATIC
    static <A extends AggregateRoot<A, I>, I extends ID> void requireID(final A entity)
    {
        requireID(entity.getId());
    }

    static <A extends AggregateRoot<A, I>, I extends ID> void requireID(final I id)
    {
        if (id == null)
        {
            throw new DomainException("ID can't be null!");
        }
    }
    //endregion
}
