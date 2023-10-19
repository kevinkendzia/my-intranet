package de.kkendzia.myintranet.domain._framework;

import java.util.Optional;
import java.util.stream.Stream;

public interface CRUDDAO<T extends HasId, I> extends DAO<T, I>
{
    Stream<T> findAll();
    long countAll();
    Optional<T> finaOneById(I id);

    void update(T entity);

    void create(T entity);
}
