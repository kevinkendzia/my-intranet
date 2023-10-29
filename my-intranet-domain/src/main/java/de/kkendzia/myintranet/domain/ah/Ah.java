package de.kkendzia.myintranet.domain.ah;

import de.kkendzia.myintranet.domain._framework.AbstractEntity;
import de.kkendzia.myintranet.domain._framework.ValueObject;
import de.kkendzia.myintranet.domain.shared.adress.Adress;
import de.kkendzia.myintranet.domain.shared.mandant.Mandant;
import de.kkendzia.myintranet.domain.shared.mitgliedsform.MembershipForm;
import de.kkendzia.myintranet.domain.shared.regulierer.Regulator;
import de.kkendzia.myintranet.domain.shared.verband.Association;

import java.time.LocalDate;

import static java.util.Objects.requireNonNull;

public final class Ah extends AbstractEntity
{
    private Ahnr ahnr;
    private String matchcode;
    private Mandant mandant;
    private LocalDate enterDate;
    private LocalDate exitDate;
    private Adress adress;
    private Regulator regulator;
    private Association association;
    private MembershipForm membershipForm;

    public Ah(
            long id,
            final Ahnr ahnr,
            final String matchcode,
            final Mandant mandant,
            final LocalDate enterDate,
            final LocalDate exitDate,
            final Adress adress,
            final Regulator regulator,
            final Association association,
            final MembershipForm membershipForm)
    {
        super(id);
        this.ahnr = ahnr;
        this.matchcode = matchcode;
        this.mandant = mandant;
        this.enterDate = enterDate;
        this.exitDate = exitDate;
        this.adress = adress;
        this.regulator = regulator;
        this.association = association;
        this.membershipForm = membershipForm;
    }

    //region SETTER / GETTER
    public Ahnr getAhnr()
    {
        return this.ahnr;
    }

    public void setAhnr(final Ahnr ahnr)
    {
        this.ahnr = requireNonNull(ahnr, "ahnr can't be null!");
    }

    public String getMatchcode()
    {
        return this.matchcode;
    }

    public void setMatchcode(final String matchcode)
    {
        this.matchcode = requireNonNull(matchcode, "matchcode can't be null!");
    }

    public Mandant getMandant()
    {
        return this.mandant;
    }

    public void setMandant(final Mandant mandant)
    {
        this.mandant = requireNonNull(mandant, "mandant can't be null!");
    }

    public LocalDate getEnterDate()
    {
        return this.enterDate;
    }

    public void setEnterDate(final LocalDate enterDate)
    {
        this.enterDate = enterDate;
    }

    public LocalDate getExitDate()
    {
        return this.exitDate;
    }

    public void setExitDate(final LocalDate exitDate)
    {
        this.exitDate = exitDate;
    }

    public Adress getAdress()
    {
        return adress;
    }

    public void setAdress(final Adress adress)
    {
        this.adress = adress;
    }

    public Regulator getRegulator()
    {
        return this.regulator;
    }

    public void setRegulator(final Regulator regulator)
    {
        this.regulator = regulator;
    }

    public Association getAssociation()
    {
        return this.association;
    }

    public void setAssociation(final Association association)
    {
        this.association = association;
    }

    public MembershipForm getMembershipForm()
    {
        return this.membershipForm;
    }

    public void setMembershipForm(final MembershipForm membershipForm)
    {
        this.membershipForm = membershipForm;
    }
    //endregion

    //region VALUE TYPES
    public record Ahnr(int value) implements ValueObject
    {
        // record
    }
    //endregion
}
