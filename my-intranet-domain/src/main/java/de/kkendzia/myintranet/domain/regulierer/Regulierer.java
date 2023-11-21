package de.kkendzia.myintranet.domain.regulierer;

import de.kkendzia.myintranet.domain._core.AbstractAggregateRoot;
import de.kkendzia.myintranet.domain._core.AbstractID;

import java.util.UUID;


/**
 * Regulierer
 */
public class Regulierer extends AbstractAggregateRoot<Regulierer, Regulierer.ReguliererID>
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
    public static class ReguliererID extends AbstractID
    {
        public ReguliererID(final UUID value)
        {
            super(value);
        }

        public ReguliererID()
        {
        }
    }
    //endregion
}
