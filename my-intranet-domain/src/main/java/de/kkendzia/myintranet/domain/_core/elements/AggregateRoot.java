package de.kkendzia.myintranet.domain._core.elements;

public interface AggregateRoot<A extends AggregateRoot<A, I>, I extends ID> extends Entity<A, I>
{
}
