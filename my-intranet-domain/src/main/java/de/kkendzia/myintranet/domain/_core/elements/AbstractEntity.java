package de.kkendzia.myintranet.domain._core.elements;

// TODO: move HasMutableId down?
public class AbstractEntity<A extends AggregateRoot<A, ?>, I extends ID> implements Entity<A, I>, HasMutableId<I>
{
    private I id;
    private int version = 0;

    public AbstractEntity()
    {
        this(null);
    }

    public AbstractEntity(I id)
    {
        this.id = id;
    }

    @Override
    public I getId()
    {
        return id;
    }

    @Override
    public void setId(I id)
    {
        this.id = id;
    }

    public int getVersion()
    {
        return version;
    }

    public void setVersion(int version)
    {
        this.version = version;
    }
}
