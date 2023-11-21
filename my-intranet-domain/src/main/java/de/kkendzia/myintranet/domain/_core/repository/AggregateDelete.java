package de.kkendzia.myintranet.domain._core.repository;

import de.kkendzia.myintranet.domain._core.AggregateRoot;
import de.kkendzia.myintranet.domain._core.ID;

public interface AggregateDelete<A extends AggregateRoot<A, I>, I extends ID>
{
    void remove(A aggregate);

    void removeByID(I id);
}
