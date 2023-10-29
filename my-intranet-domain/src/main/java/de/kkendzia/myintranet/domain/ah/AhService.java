package de.kkendzia.myintranet.domain.ah;

import de.kkendzia.myintranet.domain._framework.DomainService;

import static java.util.Objects.requireNonNull;

public class AhService implements DomainService
{
    private AhDAO ahDAO;

    public AhService(AhDAO ahDAO)
    {
        this.ahDAO = requireNonNull(ahDAO, "ahDAO can't be null!");
    }

    public void create(Ah ah)
    {
        ahDAO.create(ah);
        // 29.10.2023 KK TODO: DomainEvent? AhCreated? AdressCreated?
    }
}
