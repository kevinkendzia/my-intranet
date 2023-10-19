package de.kkendzia.myintranet.domain.shared.adress;

import java.util.Random;

public class Country
{
    private long id;
    private String name;
    private String abbreviation;

    public Country()
    {
        this(new Random().nextInt(1000), "Testland", "TL");
    }

    public Country(
            long id,
            String name,
            String abbreviation)
    {
        this.id = id;
        this.name = name;
        this.abbreviation = abbreviation;
    }

    public long getId()
    {
        return id;
    }

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

    public String getAbbreviation()
    {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation)
    {
        this.abbreviation = abbreviation;
    }
}
