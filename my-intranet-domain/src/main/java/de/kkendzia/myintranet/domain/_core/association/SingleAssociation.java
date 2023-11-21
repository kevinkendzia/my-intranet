package de.kkendzia.myintranet.domain._core.association;

import de.kkendzia.myintranet.domain._core.AggregateRoot;
import de.kkendzia.myintranet.domain._core.ID;

public record SingleAssociation<A extends AggregateRoot<A, I>, I extends ID>(I id)
{
    //region STATIC
    public static <A extends AggregateRoot<A, I>, I extends ID> SingleAssociation<A, I> fromID(
            I id)
    {
        return new SingleAssociation<>(id);
    }

    public static <A extends AggregateRoot<A, I>, I extends ID> SingleAssociation<A, I> fromAggregate(
            A aggregate)
    {
        return new SingleAssociation<>(aggregate.getId());
    }
    //endregion
}
