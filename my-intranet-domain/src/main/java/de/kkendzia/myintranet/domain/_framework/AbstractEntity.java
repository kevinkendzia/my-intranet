package de.kkendzia.myintranet.domain._framework;

public class AbstractEntity implements Entity
{
    private long id;
    private int version = 0;

    public AbstractEntity()
    {
        this(Entity.NEW_ID);
    }

    public AbstractEntity(long id)
    {
        this.id = id;
    }

    @Override
    public long getId()
    {
        return id;
    }

    @Override
    public void setId(long id)
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
