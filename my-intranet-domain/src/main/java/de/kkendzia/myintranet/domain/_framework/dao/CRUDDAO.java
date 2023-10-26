package de.kkendzia.myintranet.domain._framework.dao;

import de.kkendzia.myintranet.domain._framework.HasId;

import java.util.Optional;
import java.util.stream.Stream;

public interface CRUDDAO<T extends HasId, I> extends DAO<T, I>
{
    Stream<T> findAll();
    long countAll();
    Optional<T> findOptionalById(I id);
    default T findById(I id)
    {
        return findOptionalById(id).orElse(null);
    }

    T update(T entity);

    T create(T entity);

    void deleteById(long id);
}
