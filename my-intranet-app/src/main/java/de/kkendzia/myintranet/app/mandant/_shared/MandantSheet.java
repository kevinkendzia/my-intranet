package de.kkendzia.myintranet.app.mandant._shared;

import de.kkendzia.myintranet.domain.mandant.Mandant;

public class MandantSheet
{
    private Mandant.MandantID id;
    private String key;
    private String name;
    private byte[] image;

    public MandantSheet(final Mandant.MandantID id, String key, final String name, final byte[] image)
    {
        this.id = id;
        this.key = key;
        this.name = name;
        this.image = image;
    }

    public MandantSheet(final Mandant mandant)
    {
        this(
                mandant.getId(),
                mandant.getKey(),
                mandant.getName(),
                mandant.getImage()
        );
    }

    public Mandant.MandantID getId()
    {
        return id;
    }

    public void setId(final Mandant.MandantID id)
    {
        this.id = id;
    }

    public String getKey()
    {
        return key;
    }

    public void setKey(final String key)
    {
        this.key = key;
    }

    public String getName()
    {
        return name;
    }

    public void setName(final String name)
    {
        this.name = name;
    }

    public byte[] getImage()
    {
        return image;
    }

    public void setImage(final byte[] image)
    {
        this.image = image;
    }
}
