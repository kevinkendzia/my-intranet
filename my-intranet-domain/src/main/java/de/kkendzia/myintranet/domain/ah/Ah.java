package de.kkendzia.myintranet.domain.ah;

import de.kkendzia.myintranet.domain._core.AbstractAggregateRoot;
import de.kkendzia.myintranet.domain._core.AbstractID;
import de.kkendzia.myintranet.domain._core.SingleAssociation;
import de.kkendzia.myintranet.domain._core.ValueObject;
import de.kkendzia.myintranet.domain.ah.mitgliedsform.MitgliedsForm;
import de.kkendzia.myintranet.domain.ah.mitgliedsform.MitgliedsForm.MitgliedsFormID;
import de.kkendzia.myintranet.domain.ah.regulierer.Regulierer;
import de.kkendzia.myintranet.domain.ah.regulierer.Regulierer.ReguliererID;
import de.kkendzia.myintranet.domain.ah.verband.Verband;
import de.kkendzia.myintranet.domain.ah.verband.Verband.VerbandID;
import de.kkendzia.myintranet.domain.shared.adress.Adress;
import de.kkendzia.myintranet.domain.shared.mandant.Mandant;
import de.kkendzia.myintranet.domain.shared.mandant.Mandant.MandantID;

import java.time.LocalDate;
import java.util.UUID;

public final class Ah extends AbstractAggregateRoot<Ah, Ah.AhID>
{
    private Ahnr ahnr;
    private String matchcode;
    private LocalDate enterDate;
    private LocalDate exitDate;
    private Adress adress;

    // ASSOCIATIONS

    private SingleAssociation<Mandant, MandantID> mandant;
    private SingleAssociation<Regulierer, ReguliererID> regulator;
    private SingleAssociation<Verband, VerbandID> verband;
    private SingleAssociation<MitgliedsForm, MitgliedsFormID> membershipForm;

    public Ah(
            final Ahnr ahnr,
            final String matchcode,
            final LocalDate enterDate,
            final MandantID mandant,
            final ReguliererID regulator,
            final VerbandID verband,
            final MitgliedsFormID membershipForm)
    {
        this.ahnr = ahnr;
        this.matchcode = matchcode;
        this.enterDate = enterDate;
        this.mandant = new SingleAssociation<>(mandant);
        this.regulator = new SingleAssociation<>(regulator);
        this.verband = new SingleAssociation<>(verband);
        this.membershipForm = new SingleAssociation<>(membershipForm);
    }

    //region SETTER / GETTER

    public Ahnr getAhnr()
    {
        return ahnr;
    }

    public void setAhnr(final Ahnr ahnr)
    {
        this.ahnr = ahnr;
    }

    public String getMatchcode()
    {
        return matchcode;
    }

    public void setMatchcode(final String matchcode)
    {
        this.matchcode = matchcode;
    }

    public LocalDate getEnterDate()
    {
        return enterDate;
    }

    public void setEnterDate(final LocalDate enterDate)
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

    public Adress getAdress()
    {
        return adress;
    }

    public void setAdress(final Adress adress)
    {
        this.adress = adress;
    }

    public SingleAssociation<Mandant, MandantID> getMandant()
    {
        return mandant;
    }

    public void setMandant(final SingleAssociation<Mandant, MandantID> mandant)
    {
        this.mandant = mandant;
    }

    public SingleAssociation<Regulierer, ReguliererID> getRegulator()
    {
        return regulator;
    }

    public void setRegulator(final SingleAssociation<Regulierer, ReguliererID> regulator)
    {
        this.regulator = regulator;
    }

    public SingleAssociation<Verband, VerbandID> getVerband()
    {
        return verband;
    }

    public void setVerband(final SingleAssociation<Verband, VerbandID> verband)
    {
        this.verband = verband;
    }

    public SingleAssociation<MitgliedsForm, MitgliedsFormID> getMembershipForm()
    {
        return membershipForm;
    }

    public void setMembershipForm(final SingleAssociation<MitgliedsForm, MitgliedsFormID> membershipForm)
    {
        this.membershipForm = membershipForm;
    }


    //endregion

    //region VALUE TYPES
    public static class AhID extends AbstractID
    {
        public AhID(final UUID value)
        {
            super(value);
        }

        public AhID()
        {
            this(UUID.randomUUID());
        }
    }

    public record Ahnr(int value) implements ValueObject
    {
        // record
    }
    //endregion
}
