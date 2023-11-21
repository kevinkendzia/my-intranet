package de.kkendzia.myintranet.domain._core.repository;

import de.kkendzia.myintranet.domain._core.AggregateRoot;
import de.kkendzia.myintranet.domain._core.ID;
import de.kkendzia.myintranet.domain._core.exception.DomainException;

import java.util.Optional;

public interface AggregateUpdate<A extends AggregateRoot<A, I>, I extends ID>
{
    void update(A aggregate);
}
