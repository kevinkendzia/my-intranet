package de.kkendzia.myintranet.domain._core.association;

import de.kkendzia.myintranet.domain._core.AggregateRoot;
import de.kkendzia.myintranet.domain._core.ID;

import java.util.Set;

import static java.util.Collections.emptySet;
import static java.util.Objects.requireNonNull;

public record MultiAssociation<A extends AggregateRoot<A, I>, I extends ID>(Set<I> ids)
{
    public MultiAssociation()
    {
        this(emptySet());
    }

    public MultiAssociation(final Set<I> ids)
    {
        this.ids = requireNonNull(ids, "ids can't be null!");
    }

    public void add(final A aggregate)
    {
        ids.add(aggregate.getId());
    }

    //region STATIC
    public static <A extends AggregateRoot<A, I>, I extends ID> MultiAssociation<A, I> fromIDs(Set<I> ids)
    {
        return new MultiAssociation<>(ids);
    }

    public static <A extends AggregateRoot<A, I>, I extends ID> MultiAssociation<A, I> fromID(I id)
    {
        return new MultiAssociation<>(Set.of(id));
    }
    //endregion
}
