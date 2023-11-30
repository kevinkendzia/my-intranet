package de.kkendzia.myintranet.domain._core.repository;

import de.kkendzia.myintranet.domain._core.AggregateRoot;
import de.kkendzia.myintranet.domain._core.ID;

public interface AggregateUpdate<A extends AggregateRoot<A, I>, I extends ID>
{
    A update(A aggregate);
}