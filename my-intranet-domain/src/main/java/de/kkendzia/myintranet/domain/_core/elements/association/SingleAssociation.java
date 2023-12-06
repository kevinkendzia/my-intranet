package de.kkendzia.myintranet.domain._core.elements.association;

import de.kkendzia.myintranet.domain._core.elements.AggregateRoot;
import de.kkendzia.myintranet.domain._core.elements.ID;

import static java.util.Objects.requireNonNull;

public record SingleAssociation<A extends AggregateRoot<A, I>, I extends ID>(I id) implements Association
{
    @Override
    public boolean isEmpty()
    {
        return id == null;
    }

    //region STATIC
    public static <A extends AggregateRoot<A, I>, I extends ID> SingleAssociation<A, I> emptySingleLink()
    {
        return new SingleAssociation<>(null);
    }

    public static <A extends AggregateRoot<A, I>, I extends ID> SingleAssociation<A, I> requiredSingleLink(I id)
    {
        return new SingleAssociation<>(requireNonNull(id, "id can't be null!"));
    }

    public static <A extends AggregateRoot<A, I>, I extends ID> SingleAssociation<A, I> requiredSingleLink(A aggregate)
    {
        return requiredSingleLink(requireNonNull(aggregate, "aggregate can't be null!").getId());
    }

    public static <A extends AggregateRoot<A, I>, I extends ID> SingleAssociation<A, I> optionalSingleLink(I id)
    {
        return id != null ? new SingleAssociation<>(id) : emptySingleLink();
    }

    public static <A extends AggregateRoot<A, I>, I extends ID> SingleAssociation<A, I> optionalSingleLink(
            A aggregate)
    {
        return aggregate != null ? requiredSingleLink(aggregate.getId()) : emptySingleLink();
    }
    //endregion
}
