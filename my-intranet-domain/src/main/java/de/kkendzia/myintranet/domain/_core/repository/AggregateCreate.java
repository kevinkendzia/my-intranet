package de.kkendzia.myintranet.domain._core.repository;

import de.kkendzia.myintranet.domain._core.AggregateRoot;
import de.kkendzia.myintranet.domain._core.ID;

public interface AggregateCreate<A extends AggregateRoot<A, I>, I extends ID>
{
    A add(A aggregate);
}
