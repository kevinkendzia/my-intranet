package de.kkendzia.myintranet.domain._core.elements;

public class AbstractAggregateRoot<A extends AggregateRoot<A, I>, I extends ID>
        extends AbstractEntity<A, I>
        implements AggregateRoot<A, I>
{
    public AbstractAggregateRoot()
    {
        super(null);
    }

    public AbstractAggregateRoot(I id)
    {
        super(id);
    }
}
