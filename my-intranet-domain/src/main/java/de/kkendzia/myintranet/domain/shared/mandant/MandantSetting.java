package de.kkendzia.myintranet.domain.shared.mandant;

import de.kkendzia.myintranet.domain._framework.HasId;

public final class MandantSetting implements HasId
{
    private  long id;
    private  String name;
    private  String type;
    private String value;
    private long mandantId;

    public MandantSetting(
            long id,
            String name,
            String type,
            String value,
            long mandantId)
    {
        this.id = id;
        this.name = name;
        this.type = type;
        this.value = value;
        this.mandantId=mandantId;
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

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getType()
    {
        return type;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    public long getMandantId()
    {
        return mandantId;
    }

    public void setMandantId(long mandantId)
    {
        this.mandantId = mandantId;
    }
}