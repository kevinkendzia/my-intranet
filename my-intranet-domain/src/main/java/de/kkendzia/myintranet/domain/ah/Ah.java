package de.kkendzia.myintranet.domain.ah;

import de.kkendzia.myintranet.domain._framework.AbstractEntity;
import de.kkendzia.myintranet.domain._framework.ValueObject;
import de.kkendzia.myintranet.domain.shared.mandant.Mandant;

import java.time.LocalDate;

import static java.util.Objects.requireNonNull;

public final class Ah extends AbstractEntity
{
    private Ahnr ahnr;
    private String matchcode;
    private Mandant mandant;
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
        super(id);
        this.ahnr = ahnr;
        this.matchcode = matchcode;
        this.mandant = mandant;
        this.enterDate = enterDate;
        this.exitDate = exitDate;
    }

    //region SETTER / GETTER
    public Ahnr getAhnr()
    {
        return ahnr;
    }

    public void setAhnr(Ahnr ahnr)
    {
        this.ahnr = requireNonNull(ahnr, "ahnr can't be null!");
    }

    public String getMatchcode()
    {
        return matchcode;
    }

    public void setMatchcode(String matchcode)
    {
        this.matchcode = requireNonNull(matchcode, "matchcode can't be null!");
    }

    public Mandant getMandant()
    {
        return mandant;
    }

    public void setMandant(Mandant mandant)
    {
        this.mandant = requireNonNull(mandant, "mandant can't be null!");
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

    //region VALUE TYPES
    public record Ahnr(int value) implements ValueObject
    {
        // record
    }
    //endregion
}
