package de.kkendzia.myintranet.domain._core.repository;

import de.kkendzia.myintranet.domain._core.AggregateRoot;
import de.kkendzia.myintranet.domain._core.ID;

public interface Repository<A extends AggregateRoot<A, I>, I extends ID>
{
    void add(A root);
}
