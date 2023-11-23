package de.kkendzia.myintranet.app.ah.commands;

import de.kkendzia.myintranet.app.mandant.queries.ListMandanten.MandantItem;
import de.kkendzia.myintranet.domain.ah.Ah;

import java.time.LocalDate;

public class AhCoreData
{
    private Ah.Ahnr ahnr;
    private String matchcode;
    private MandantItem mandant;
    private LocalDate enterDate;
    private LocalDate exitDate;

    public AhCoreData(
            Ah.Ahnr ahnr,
            String matchcode,
            MandantItem mandant,
            LocalDate enterDate,
            LocalDate exitDate)
    {
        this.ahnr = ahnr;
        this.matchcode = matchcode;
        this.mandant = mandant;
        this.enterDate = enterDate;
        this.exitDate = exitDate;
    }

    public Ah.Ahnr getAhnr()
    {
        return ahnr;
    }

    public void setAhnr(Ah.Ahnr ahnr)
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

    public MandantItem getMandant()
    {
        return mandant;
    }

    public void setMandant(MandantItem mandant)
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

    public void setExitDate(final LocalDate exitDate)
    {
        this.exitDate = exitDate;
    }
}
