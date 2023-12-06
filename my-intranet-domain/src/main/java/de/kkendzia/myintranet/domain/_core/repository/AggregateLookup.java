package de.kkendzia.myintranet.domain._core.repository;

import de.kkendzia.myintranet.domain._core.elements.AggregateRoot;
import de.kkendzia.myintranet.domain._core.elements.ID;
import de.kkendzia.myintranet.domain._core.exception.DomainException;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

public interface AggregateLookup<A extends AggregateRoot<A, I>, I extends ID>
{
    Optional<A> findByID(I id);

    default A getByID(I id)
    {
        requireNonNull(id, "id can't be null!");
        return findByID(id).orElseThrow(() -> new DomainException("Couldn't find " + getClass() + " by id " + id));
    }

    Stream<A> findAllByID(Collection<I> ids);

    default List<A> getAllByID(Collection<I> ids)
    {
        return findAllByID(ids).toList();
    }
}
