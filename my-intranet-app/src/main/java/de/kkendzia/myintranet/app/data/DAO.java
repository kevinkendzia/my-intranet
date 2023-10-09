package de.kkendzia.myintranet.app.data;

import de.kkendzia.myintranet.domain.ah.Ah;

import java.util.List;
import java.util.Optional;

public interface DAO<T, ID>
{
    List<T> findAll();
    Optional<T> findOneById(ID id);
}
