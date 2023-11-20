package de.kkendzia.myintranet.domain._core;

public class SingleAssociation<A extends AggregateRoot<A, I>, I extends ID> implements Association<A, I>
{
    private final I id;

    public SingleAssociation(I id)
    {
        this.id = id;
    }
}
