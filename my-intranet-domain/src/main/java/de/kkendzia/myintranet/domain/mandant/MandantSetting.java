package de.kkendzia.myintranet.domain.mandant;

import de.kkendzia.myintranet.domain._core.AbstractEntity;
import de.kkendzia.myintranet.domain._core.AbstractID;

import java.util.UUID;

public final class MandantSetting extends AbstractEntity<Mandant, MandantSetting.MandantSettingID>
{
    private final String name;
    private final String type;
    private String value;

    public MandantSetting(
            MandantSettingID id,
            String name,
            String type,
            String value)
    {
        super(id);
        this.name = name;
        this.type = type;
        this.value = value;
    }

    //region GETTER

    public String getName()
    {
        return name;
    }

    public String getType()
    {
        return type;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue(final String value)
    {
        this.value = value;
    }

    //endregion

    //region TYPES
    public static class MandantSettingID extends AbstractID
    {
        public MandantSettingID(final UUID value)
        {
            super(value);
        }

        public MandantSettingID()
        {
        }
    }
    //endregion
}
