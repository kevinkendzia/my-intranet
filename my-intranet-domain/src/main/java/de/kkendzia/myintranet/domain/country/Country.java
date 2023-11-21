package de.kkendzia.myintranet.domain.country;

import de.kkendzia.myintranet.domain._core.AbstractAggregateRoot;
import de.kkendzia.myintranet.domain._core.AbstractID;

import java.util.UUID;

public class Country extends AbstractAggregateRoot<Country, Country.CountryID>
{
    private String name;
    private String abbreviation;

    public Country(
            CountryID id,
            String name,
            String abbreviation)
    {
        super(id);
        this.name = name;
        this.abbreviation = abbreviation;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getAbbreviation()
    {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation)
    {
        this.abbreviation = abbreviation;
    }

    //region TYPES
    public static class CountryID extends AbstractID
    {
        public CountryID(final UUID value)
        {
            super(value);
        }

        public CountryID()
        {
        }
    }
    //endregion
}
