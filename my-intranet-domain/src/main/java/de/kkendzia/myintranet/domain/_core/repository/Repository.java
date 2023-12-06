package de.kkendzia.myintranet.domain._core.repository;

import de.kkendzia.myintranet.domain._core.elements.AggregateRoot;
import de.kkendzia.myintranet.domain._core.elements.ID;
import de.kkendzia.myintranet.domain._core.exception.DomainException;

public interface Repository<A extends AggregateRoot<A, I>, I extends ID>
        extends
        AggregateList<A>,
        AggregateLookup<A, I>,
        AssociationResolver<A, I>,
        AggregateCreate<A, I>,
        AggregateUpdate<A, I>,
        AggregateDelete<A, I>
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
