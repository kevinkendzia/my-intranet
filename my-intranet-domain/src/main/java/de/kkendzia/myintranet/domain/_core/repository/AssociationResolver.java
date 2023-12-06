package de.kkendzia.myintranet.domain._core.repository;

import de.kkendzia.myintranet.domain._core.elements.AggregateRoot;
import de.kkendzia.myintranet.domain._core.elements.ID;
import de.kkendzia.myintranet.domain._core.elements.association.MultiAssociation;
import de.kkendzia.myintranet.domain._core.elements.association.SingleAssociation;

import java.util.List;
import java.util.Optional;

public interface AssociationResolver<A extends AggregateRoot<A, I>, I extends ID>
        extends
        AggregateLookup<A, I>
{
    default Optional<A> resolve(SingleAssociation<A, I> association)
    {
        return findByID(association.id());
    }

    default A resolveRequired(SingleAssociation<A, I> association)
    {
        return resolve(association).orElseThrow(
                () -> new IllegalArgumentException(
                        String.format("Could not resolve association %s!", association)));
    }

    default List<A> resolve(MultiAssociation<A, I> association)
    {
        return getAllByID(association.ids());
    }
}
