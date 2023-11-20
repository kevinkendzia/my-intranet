package de.kkendzia.myintranet.domain._core;

public interface Entity<A extends AggregateRoot<A, ?>, I extends ID> extends HasId<I>
{
    default boolean isNew()
    {
        return getId() == null;
    }
}
