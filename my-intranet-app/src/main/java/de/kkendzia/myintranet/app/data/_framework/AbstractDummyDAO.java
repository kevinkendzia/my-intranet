package de.kkendzia.myintranet.app.data._framework;

import de.kkendzia.myintranet.domain._framework.CRUDDAO;

import java.util.List;
import java.util.stream.IntStream;

public abstract class AbstractDummyDAO<T>
        implements CRUDDAO<T, Long>
{
    private int count;

    protected AbstractDummyDAO(int count)
    {
        this.count = count;
    }

    protected abstract T createEntity(int i);

    @Override
    public List<T> findAll()
    {
        return IntStream.rangeClosed(1,count).mapToObj(this::createEntity).toList();
    }

    @Override
    public T getOneById(Long id)
    {
        return createEntity(Math.toIntExact(id));
    }
}
