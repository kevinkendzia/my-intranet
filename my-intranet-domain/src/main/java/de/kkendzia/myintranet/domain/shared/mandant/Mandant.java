package de.kkendzia.myintranet.domain.shared.mandant;

import de.kkendzia.myintranet.domain._framework.AbstractEntity;
import de.kkendzia.myintranet.domain._framework.HasId;

public final class Mandant extends AbstractEntity implements HasId
{
    private  String key;
    private  String name;
    private byte[] image;

    public Mandant(
            long id,
            String key,
            String name)
    {
        super(id);
        this.key=key;
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
}
