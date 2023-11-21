package de.kkendzia.myintranet.domain._core.repository;

import de.kkendzia.myintranet.domain._core.AggregateRoot;
import de.kkendzia.myintranet.domain._core.ID;
import de.kkendzia.myintranet.domain._core.exception.DomainException;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface AggregateLookup<A extends AggregateRoot<A, I>, I extends ID>
{
    Optional<A> findByID(I id);

    default A getByID(I id)
    {
        return findByID(id).orElseThrow(() -> new DomainException("Couldn't find Entity by id " + id));
    }

    Stream<A> findAllByID(Collection<I> ids);

    default List<A> getAllByID(Collection<I> ids)
    {
        return findAllByID(ids).toList();
    }
}
