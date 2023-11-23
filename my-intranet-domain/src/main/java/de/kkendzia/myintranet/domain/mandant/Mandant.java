package de.kkendzia.myintranet.domain.mandant;

import de.kkendzia.myintranet.domain._core.AbstractAggregateRoot;
import de.kkendzia.myintranet.domain._core.AbstractID;

import java.util.UUID;

public final class Mandant extends AbstractAggregateRoot<Mandant, Mandant.MandantID>
{
    public static final String PROPERTY_NAME = "name";

    private String key;
    private String name;
    private byte[] image;

    public Mandant(
            MandantID id,
            String key,
            String name)
    {
        super(id);
        this.key = key;
        this.name = name;
    }

    public Mandant(final String key, final String name)
    {
        this.key = key;
        this.name = name;
    }

    public String getKey()
    {
        return key;
    }

    public void setKey(String key)
    {
        this.key = key;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public byte[] getImage()
    {
        return this.image;
    }

    public void setImage(byte[] image)
    {
        this.image = image;
    }

    //region TYPES
    public static class MandantID extends AbstractID
    {
        public MandantID(final UUID value)
        {
            super(value);
        }

        public MandantID(final String value)
        {
            super(value);
        }

        public MandantID()
        {
            super();
        }
    }
    //endregion
}
