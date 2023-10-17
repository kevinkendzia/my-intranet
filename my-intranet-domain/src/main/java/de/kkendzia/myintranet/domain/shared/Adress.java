package de.kkendzia.myintranet.domain.shared;

import de.kkendzia.myintranet.domain._framework.HasId;

import java.util.Random;

public class Adress implements HasId
{
    private long id;
    private String line1;
    private String line2;
    private String street;
    private String zip;
    private String city;
    private Country country;

    public Adress()
    {
        this(new Random().nextInt(1000), "Das ist ein Test!", "Test", "Teststraße", "12345", "Testcity", new Country());
    }

    public Adress(
            long id,
            String line1,
            String line2,
            String street,
            String zip,
            String city,
            Country country)
    {
        this.id = id;
        this.line1 = line1;
        this.line2 = line2;
        this.street = street;
        this.zip = zip;
        this.city = city;
        this.country = country;
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getLine1()
    {
        return line1;
    }

    public void setLine1(String line1)
    {
        this.line1 = line1;
    }

    public String getLine2()
    {
        return line2;
    }

    public void setLine2(String line2)
    {
        this.line2 = line2;
    }

    public String getStreet()
    {
        return street;
    }

    public void setStreet(String street)
    {
        this.street = street;
    }

    public String getZip()
    {
        return zip;
    }

    public void setZip(String zip)
    {
        this.zip = zip;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public Country getCountry()
    {
        return country;
    }

    public void setCountry(Country country)
    {
        this.country = country;
    }
}
