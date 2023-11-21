package de.kkendzia.myintranet.domain.ah;

import de.kkendzia.myintranet.domain._core.AbstractAggregateRoot;
import de.kkendzia.myintranet.domain._core.AbstractID;
import de.kkendzia.myintranet.domain._core.ValueObject;
import de.kkendzia.myintranet.domain._core.association.SingleAssociation;
import de.kkendzia.myintranet.domain.mandant.Mandant;
import de.kkendzia.myintranet.domain.mandant.Mandant.MandantID;
import de.kkendzia.myintranet.domain.mitgliedsform.MitgliedsForm;
import de.kkendzia.myintranet.domain.mitgliedsform.MitgliedsForm.MitgliedsFormID;
import de.kkendzia.myintranet.domain.regulierer.Regulierer;
import de.kkendzia.myintranet.domain.regulierer.Regulierer.ReguliererID;
import de.kkendzia.myintranet.domain.verband.Verband;
import de.kkendzia.myintranet.domain.verband.Verband.VerbandID;

import java.time.LocalDate;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

public final class Ah extends AbstractAggregateRoot<Ah, Ah.AhID>
{
    private final Ahnr ahnr;
    private final String matchcode;
    private final LocalDate enterDate;
    private LocalDate exitDate;
    private AhAdress adress;

    // ASSOCIATIONS

    private final SingleAssociation<Mandant, MandantID> mandant;
    private final SingleAssociation<Regulierer, ReguliererID> regulator;
    private final SingleAssociation<Verband, VerbandID> verband;
    private final SingleAssociation<MitgliedsForm, MitgliedsFormID> membershipForm;

    public Ah(
            final Ahnr ahnr,
            final String matchcode,
            final LocalDate enterDate,
            final MandantID mandant,
            final ReguliererID regulator,
            final VerbandID verband,
            final MitgliedsFormID membershipForm)
    {
        this.ahnr = requireNonNull(ahnr, "ahnr can't be null!");
        this.matchcode = requireNonNull(matchcode, "matchcode can't be null!");

        this.enterDate = enterDate;
        this.mandant = SingleAssociation.fromID(mandant);
        this.regulator = SingleAssociation.fromID(regulator);
        this.verband = SingleAssociation.fromID(verband);
        this.membershipForm = SingleAssociation.fromID(membershipForm);
    }

    public void exitNow()
    {
        this.exitDate = LocalDate.now();
    }

    //region GETTER
    public Ahnr getAhnr()
    {
        return ahnr;
    }

    public String getMatchcode()
    {
        return matchcode;
    }

    public LocalDate getEnterDate()
    {
        return enterDate;
    }

    public LocalDate getExitDate()
    {
        return exitDate;
    }

    public AhAdress getAdress()
    {
        return adress;
    }

    public SingleAssociation<Mandant, MandantID> getMandant()
    {
        return mandant;
    }

    public SingleAssociation<Regulierer, ReguliererID> getRegulator()
    {
        return regulator;
    }

    public SingleAssociation<Verband, VerbandID> getVerband()
    {
        return verband;
    }

    public SingleAssociation<MitgliedsForm, MitgliedsFormID> getMembershipForm()
    {
        return membershipForm;
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
