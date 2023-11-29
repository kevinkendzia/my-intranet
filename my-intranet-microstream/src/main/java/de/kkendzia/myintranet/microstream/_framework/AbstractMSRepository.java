package de.kkendzia.myintranet.microstream._framework;

import de.kkendzia.myintranet.domain._core.AggregateRoot;
import de.kkendzia.myintranet.domain._core.ID;
import de.kkendzia.myintranet.domain._core.repository.Repository;
import one.microstream.storage.types.StorageManager;

import java.util.*;
import java.util.stream.Stream;

import static de.kkendzia.myintranet.domain._core.repository.Repository.requireID;
import static java.util.Objects.requireNonNull;

public abstract class AbstractMSRepository<A extends AggregateRoot<A, I>, I extends ID>
        implements Repository<A, I>, HasStorageManager
{
    private final StorageManager storageManager;

    protected AbstractMSRepository(
            final StorageManager storageManager)
    {
        this.storageManager = storageManager;
    }

    public StorageManager getStorageManager()
    {
        return storageManager;
    }

    protected abstract Map<I, A> getRootCollection();

    @Override
    public long countAll()
    {
        return listAll().size();
    }

    @Override
    public List<A> listAll()
    {
        return new ArrayList<>(getRootCollection().values());
    }

    @Override
    public Stream<A> list(final int offset, final int limit)
    {
        return listAll().stream().skip(offset).limit(limit);
    }

    @Override
    public Optional<A> findByID(final I id)
    {
        return listAll().stream().filter(x -> Objects.equals(x.getId(), id)).findFirst();
    }

    @Override
    public Stream<A> findAllByID(final Collection<I> ids)
    {
        return listAll().stream().filter(x -> ids.stream().anyMatch(id -> Objects.equals(x.getId(), id)));
    }

    @Override
    public A update(A entity)
    {
        requireNonNull(entity, "entity can't be null!");
        requireID(entity);

        final var rootCollection = getRootCollection();
        final var existing = rootCollection.get(entity.getId());
        if (existing == null)
        {
            throw new IllegalStateException("Entity " + entity.getClass() + " with ID " + entity.getId() + " does not exist!");
        }
        rootCollection.put(entity.getId(), entity);
        getStorageManager().store(rootCollection);
        storeAssociations(entity);

        return entity;
    }

    protected void storeAssociations(final A entity)
    {
        // optional
    }


    @Override
    public A add(A entity)
    {
        requireNonNull(entity, "entity can't be null!");
        requireID(entity);

        final var rootCollection = getRootCollection();
        final var existing = rootCollection.get(entity.getId());
        if (existing != null)
        {
            throw new IllegalStateException("Entity " + entity.getClass() + " with ID " + entity.getId() + " does already exist!");
        }
        rootCollection.put(entity.getId(), entity);
        getStorageManager().store(rootCollection);
        storeAssociations(entity);

        return entity;
    }

    @Override
    public void remove(final A entity)
    {
        requireNonNull(entity, "entity can't be null!");
        requireID(entity);

        final var rootCollection = getRootCollection();
        final var existing = rootCollection.get(entity.getId());
        if (existing == null)
        {
            throw new IllegalStateException("Entity " + entity.getClass() + " with ID " + entity.getId() + " does not exist!");
        }
        rootCollection.remove(entity.getId());
        getStorageManager().store(rootCollection);
    }

    @Override
    public void removeByID(I id)
    {
        requireID(id);

        final var rootCollection = getRootCollection();
        final var existing = rootCollection.get(id);
        if (existing == null)
        {
            throw new IllegalStateException("Entity with ID " + id + " does not exist!");
        }
        rootCollection.remove(id);
        getStorageManager().store(rootCollection);
    }
}
