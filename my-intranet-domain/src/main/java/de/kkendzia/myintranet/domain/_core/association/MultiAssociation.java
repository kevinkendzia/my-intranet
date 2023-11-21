package de.kkendzia.myintranet.domain._core.association;

import de.kkendzia.myintranet.domain._core.AggregateRoot;
import de.kkendzia.myintranet.domain._core.ID;

import java.util.List;
import java.util.function.Supplier;

import static java.util.Objects.requireNonNull;

public interface MultiAssociation<A extends AggregateRoot<A, I>, I extends ID>
{
    class MultiAssociationImpl<A extends AggregateRoot<A, I>, I extends ID> implements MultiAssociation<A, I>
    {
        private final List<I> ids;

        public MultiAssociationImpl(final List<I> ids)
        {
            this.ids = requireNonNull(ids, "ids can't be null!");
        }

        public static <A extends AggregateRoot<A, I>, I extends ID> MultiAssociation<A, I> fromID(I id)
        {
            return new MultiAssociationImpl<>(List.of(id));
        }

        public List<I> getIds()
        {
            return ids;
        }
    }
}
