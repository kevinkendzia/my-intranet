package de.kkendzia.myintranet.domain.shared.mandant;

import de.kkendzia.myintranet.domain._framework.HasId;

import java.util.Objects;
import java.util.StringJoiner;

public final class MandantSetting implements HasId
{
    private  long id;
    private long mandantId;
    private  String name;
    private  String type;
    private String value;

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

    //region OVERRIDES
    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }
        MandantSetting setting = (MandantSetting) o;
        return getId() == setting.getId() && getMandantId() == setting.getMandantId();
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(getId(), getMandantId());
    }

    @Override
    public String toString()
    {
        return new StringJoiner(", ", MandantSetting.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("mandantId=" + mandantId)
                .add("name='" + name + "'")
                .add("type='" + type + "'")
                .add("value='" + value + "'")
                .toString();
    }
    //endregion

    //region SETTER / GETTER
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
    //endregion
}
