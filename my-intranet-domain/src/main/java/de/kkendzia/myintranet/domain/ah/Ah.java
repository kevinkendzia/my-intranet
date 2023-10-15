package de.kkendzia.myintranet.domain.ah;

import de.kkendzia.myintranet.domain.shared.Mandant;

public final class Ah
{
    private  long id;
    private  Ahnr ahnr;
    private  String matchcode;
    private  Mandant mandant;

    public Ah(
            long id,
            Ahnr ahnr,
            String matchcode,
            Mandant mandant)
    {
        this.id = id;
        this.ahnr = ahnr;
        this.matchcode = matchcode;
        this.mandant = mandant;
    }

    //region SETTER / GETTER
    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public Ahnr getAhnr()
    {
        return ahnr;
    }

    public void setAhnr(Ahnr ahnr)
    {
        this.ahnr = ahnr;
    }

    public String getMatchcode()
    {
        return matchcode;
    }

    public void setMatchcode(String matchcode)
    {
        this.matchcode = matchcode;
    }

    public Mandant getMandant()
    {
        return mandant;
    }

    public void setMandant(Mandant mandant)
    {
        this.mandant = mandant;
    }
    //endregion

    /*
     * TYPES
     */

    public record Ahnr(int value)
    {
        // record
    }
}
