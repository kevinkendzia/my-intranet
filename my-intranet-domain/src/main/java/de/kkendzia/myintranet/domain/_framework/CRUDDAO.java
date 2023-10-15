package de.kkendzia.myintranet.domain._framework;

import java.util.List;
import java.util.Optional;

public interface CRUDDAO<T, I> extends DAO<T, I>
{
    List<T> findAll();
    Optional<T> finaOneById(I id);
}
