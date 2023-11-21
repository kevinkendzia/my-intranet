package de.kkendzia.myintranet.domain.verband;

import de.kkendzia.myintranet.domain._core.AbstractAggregateRoot;
import de.kkendzia.myintranet.domain._core.AbstractID;

import java.util.UUID;

/**
 * Verband
 */
public class Verband extends AbstractAggregateRoot<Verband, Verband.VerbandID>
{
    private String name;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    //region TYPES
    public static class VerbandID extends AbstractID
    {
        public VerbandID(final UUID value)
        {
            super(value);
        }

        public VerbandID()
        {
        }
    }
    //endregion
}
