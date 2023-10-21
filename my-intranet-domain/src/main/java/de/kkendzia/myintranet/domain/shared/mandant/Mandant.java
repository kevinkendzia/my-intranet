package de.kkendzia.myintranet.domain.shared.mandant;

import de.kkendzia.myintranet.domain._framework.HasId;

public final class Mandant implements HasId
{
    private  long id;
    private  String key;
    private  String name;
    private byte[] image;

    public Mandant(
            long id,
            String key,
            String name)
    {
        this.id = id;
        this.key=key;
        this.name = name;
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
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
}
