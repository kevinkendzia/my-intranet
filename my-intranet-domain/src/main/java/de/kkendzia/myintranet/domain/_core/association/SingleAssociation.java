package de.kkendzia.myintranet.domain._core.association;

import de.kkendzia.myintranet.domain._core.AggregateRoot;
import de.kkendzia.myintranet.domain._core.HasId;
import de.kkendzia.myintranet.domain._core.ID;

import java.util.function.Supplier;

import static java.util.Objects.requireNonNull;

public interface SingleAssociation<A extends AggregateRoot<A, I>, I extends ID> extends HasId<I>
{
    class SingleAssociationImpl<A extends AggregateRoot<A, I>, I extends ID> implements SingleAssociation<A, I>
    {
        private Supplier<I> idSupplier;

        public SingleAssociationImpl(final Supplier<I> idSupplier)
        {
            this.idSupplier = requireNonNull(idSupplier, "idSupplier can't be null!");
        }

        public static <A extends AggregateRoot<A, I>, I extends ID> SingleAssociationImpl<A, I> fromID(I id)
        {
            return new SingleAssociationImpl<>(() -> id);
        }
        public static <A extends AggregateRoot<A, I>, I extends ID> SingleAssociationImpl<A, I> fromAggregate(A aggregate)
        {
            return new SingleAssociationImpl<>(aggregate::getId);
        }

        @Override
        public I getId()
        {
            return idSupplier.get();
        }
    }
}
