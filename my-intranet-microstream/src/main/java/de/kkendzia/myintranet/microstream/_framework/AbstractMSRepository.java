package de.kkendzia.myintranet.microstream._framework;

import de.kkendzia.myintranet.domain._core.AggregateRoot;
import de.kkendzia.myintranet.domain._core.ID;
import de.kkendzia.myintranet.domain._core.repository.*;
import de.kkendzia.myintranet.microstream._core.MyIntranetRoot;
import one.microstream.storage.types.StorageManager;

import java.util.*;
import java.util.stream.Stream;

import static de.kkendzia.myintranet.domain._core.repository.Repository.requireID;

public abstract class AbstractMSRepository<A extends AggregateRoot<A, I>, I extends ID> implements Repository<A, I>
{
    private final MyIntranetRoot root;
    private final StorageManager storageManager;

    protected AbstractMSRepository(
            final MyIntranetRoot root,
            final StorageManager storageManager)
    {
        this.root = root;
        this.storageManager = storageManager;
    }

    protected StorageManager getStorageManager()
    {
        return storageManager;
    }

    protected MyIntranetRoot getRoot()
    {
        return root;
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
        requireID(entity);
        List<A> rootCollection = listAll();
        rootCollection.replaceAll(x -> Objects.equals(x, entity) ? entity : x);
        getStorageManager().store(rootCollection);

        return entity;
    }


    @Override
    public A add(A aggregate)
    {
        requireID(aggregate);
        List<A> rootCollection = listAll();
        rootCollection.add(aggregate);
        getStorageManager().store(rootCollection);
        return aggregate;
    }

    @Override
    public void remove(final A aggregate)
    {
        requireID(aggregate);
        List<A> rootCollection = listAll();
        rootCollection.removeIf(x -> Objects.equals(x, aggregate));
        getStorageManager().store(rootCollection);
    }

    @Override
    public void removeByID(I id)
    {
        requireID(id);
        List<A> rootCollection = listAll();
        rootCollection.removeIf(x -> Objects.equals(x.getId(), id));
        getStorageManager().store(rootCollection);
    }
}
