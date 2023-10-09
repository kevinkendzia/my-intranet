package de.kkendzia.myintranet.app.data;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

public abstract class AbstractDummyDAO<T>
        implements DAO<T, Long>
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
    public Optional<T> findOneById(Long id)
    {
        return Optional.of(createEntity(Math.toIntExact(id)));
    }
}
