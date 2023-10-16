package de.kkendzia.myintranet.domain.ah;

import de.kkendzia.myintranet.domain.shared.Mandant;

import java.time.LocalDate;

public final class Ah
{
    private  long id;
    private  Ahnr ahnr;
    private  String matchcode;
    private  Mandant mandant;
    private LocalDate enterDate;
    private LocalDate exitDate;

    public Ah(
            long id,
            Ahnr ahnr,
            String matchcode,
            Mandant mandant,
            LocalDate enterDate,
            LocalDate exitDate)
    {
        this.id = id;
        this.ahnr = ahnr;
        this.matchcode = matchcode;
        this.mandant = mandant;
        this.enterDate = enterDate;
        this.exitDate = exitDate;
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

    public LocalDate getEnterDate()
    {
        return enterDate;
    }

    public void setEnterDate(LocalDate enterDate)
    {
        this.enterDate = enterDate;
    }

    public LocalDate getExitDate()
    {
        return exitDate;
    }

    public void setExitDate(LocalDate exitDate)
    {
        this.exitDate = exitDate;
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
