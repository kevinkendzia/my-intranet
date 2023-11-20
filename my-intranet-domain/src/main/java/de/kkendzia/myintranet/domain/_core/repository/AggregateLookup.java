package de.kkendzia.myintranet.domain._core.repository;

import de.kkendzia.myintranet.domain._core.AggregateRoot;
import de.kkendzia.myintranet.domain._core.ID;

import java.util.Optional;

public interface AggregateLookup<A extends AggregateRoot<A, I>, I extends ID>
{
    Optional<A> findByID(I id);
}
