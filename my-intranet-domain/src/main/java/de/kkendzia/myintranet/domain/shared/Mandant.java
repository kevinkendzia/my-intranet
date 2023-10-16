package de.kkendzia.myintranet.domain.shared;

public final class Mandant
{
    private final long id;
    private final String name;

    public Mandant(
            long id,
            String name)
    {
        this.id = id;
        this.name = name;
    }

    public long getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }
}
