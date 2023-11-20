package de.kkendzia.myintranet.domain._core.repository;

import de.kkendzia.myintranet.domain._core.AggregateRoot;
import de.kkendzia.myintranet.domain._core.Association;
import de.kkendzia.myintranet.domain._core.ID;

import java.util.Optional;

public interface AssociationResolver<A extends AggregateRoot<A, I>, I extends ID> extends AggregateLookup<A, I>
{
    default Optional<A> resolve(Association<A, I> association)
    {
        return findByID(association.getId());
    }

    default A resolveRequired(Association<A, I> association)
    {
        return resolve(association).orElseThrow(
                () -> new IllegalArgumentException(
                        String.format("Could not resolve association %s!", association)));
    }
}
