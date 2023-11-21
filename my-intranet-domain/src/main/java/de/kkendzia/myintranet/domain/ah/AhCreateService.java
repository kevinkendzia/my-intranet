package de.kkendzia.myintranet.domain.ah;

import de.kkendzia.myintranet.domain._core.service.DomainService;
import de.kkendzia.myintranet.domain.mitgliedsform.MitgliedsForm.MitgliedsFormID;
import de.kkendzia.myintranet.domain.regulierer.Regulierer.ReguliererID;
import de.kkendzia.myintranet.domain.verband.Verband.VerbandID;
import de.kkendzia.myintranet.domain.mandant.Mandant.MandantID;

import java.time.LocalDate;

import static java.util.Objects.requireNonNull;

public class AhCreateService implements DomainService
{
    private final AhRepository repository;

    public AhCreateService(AhRepository repository)
    {
        this.repository = requireNonNull(repository, "repository can't be null!");
    }

    public void create(AhCreationRequest request)
    {
        final var ah = new Ah(
                request.ahnr(),
                request.matchcode(),
                request.enterDate(),
                request.mandant(),
                request.regulator(),
                request.verband(),
                request.membershipForm());

        repository.add(ah);
        // 29.10.2023 KK TODO: DomainEvent? AhCreated? AdressCreated?
    }

    public record AhCreationRequest(
            Ah.Ahnr ahnr,
            String matchcode,
            LocalDate enterDate,
            MandantID mandant,
            ReguliererID regulator,
            VerbandID verband,
            MitgliedsFormID membershipForm)
    {
        // just a record
    }
}
