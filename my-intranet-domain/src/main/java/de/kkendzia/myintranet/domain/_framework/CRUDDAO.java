package de.kkendzia.myintranet.domain._framework;

import java.util.List;

public interface CRUDDAO<T, I> extends DAO<T, I>
{
    List<T> findAll();
    T getOneById(I id);
}
