package de.kkendzia.myintranet.domain._core.association;

import de.kkendzia.myintranet.domain._core.AggregateRoot;
import de.kkendzia.myintranet.domain._core.ID;

import java.util.HashSet;
import java.util.Set;

import static java.util.Objects.requireNonNull;

public record MultiAssociation<A extends AggregateRoot<A, I>, I extends ID>(Set<I> ids) implements Association
{
    public MultiAssociation()
    {
        this(new HashSet<>());
    }

    public MultiAssociation(final Set<I> ids)
    {
        this.ids = new HashSet<>(requireNonNull(ids, "ids can't be null!"));
    }

    @Override
    public boolean isEmpty()
    {
        return ids.isEmpty();
    }

    public void add(final A aggregate)
    {
        ids.add(aggregate.getId());
    }

    //region STATIC
    public static <A extends AggregateRoot<A, I>, I extends ID> MultiAssociation<A, I> emptyMultLink()
    {
        return new MultiAssociation<>();
    }

    public static <A extends AggregateRoot<A, I>, I extends ID> MultiAssociation<A, I> fromIDs(Set<I> ids)
    {
        return new MultiAssociation<>(new HashSet<>(ids));
    }

    public static <A extends AggregateRoot<A, I>, I extends ID> MultiAssociation<A, I> fromID(I id)
    {
        return new MultiAssociation<>(new HashSet<>(Set.of(id)));
    }
    //endregion
}
