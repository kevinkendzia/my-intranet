package de.kkendzia.myintranet.microstream._framework;

import de.kkendzia.myintranet.domain._framework.dao.CRUDDAO;
import de.kkendzia.myintranet.domain._core.HasId;
import de.kkendzia.myintranet.microstream.MyIntranetRoot;
import de.kkendzia.myintranet.microstream._framework.id.IdHandler;
import one.microstream.storage.types.StorageManager;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.stream.Stream;

import static de.kkendzia.myintranet.domain._utils.Reduce.toOnlyElement;
import static java.util.Objects.requireNonNull;

public abstract class AbstractMSDAO<T extends HasId, I> implements CRUDDAO<T, I>
{
    @Autowired
    private MyIntranetRoot root;
    @Autowired
    private StorageManager manager;
    @Autowired
    private IdHandler idHandler;
    private final Function<MyIntranetRoot, List<T>> rootCollectionProvider;
    private BiPredicate<T, T> equalsPredicate = Objects::equals;

    protected AbstractMSDAO(Function<MyIntranetRoot, List<T>> rootCollectionProvider)
    {
        this.rootCollectionProvider = requireNonNull(rootCollectionProvider, "rootCollectionProvider can't be null!");
    }

    protected MyIntranetRoot getRoot()
    {
        return root;
    }

    protected StorageManager getManager()
    {
        return manager;
    }

    @Override
    public Stream<T> findAll()
    {
        return rootCollectionProvider.apply(getRoot()).stream();
    }

    @Override
    public long countAll()
    {
        return findAll().count();
    }

    @Override
    public Optional<T> findOptionalById(I id)
    {
        return findAll().filter(a -> Objects.equals(a.getId(), id)).reduce(toOnlyElement());
    }

    @Override
    public T update(T entity)
    {
        if (entity.getId() <= 0)
        {
            throw new IllegalArgumentException("Can't update entity with id <= 0!");
        }
        List<T> rootCollection = rootCollectionProvider.apply(getRoot());
        rootCollection.replaceAll(x -> equalsPredicate.test(x, entity) ? entity : x);
        getManager().store(rootCollection);

        return entity;
    }

    @Override
    public T create(T entity)
    {
        if (entity.getId() > 0)
        {
            throw new IllegalArgumentException("Can't create new entity with id != 0!");
        }

        long newId = idHandler.getNewId();
        entity.setId(newId);

        List<T> rootCollection = rootCollectionProvider.apply(getRoot());
        rootCollection.add(entity);
        getManager().store(rootCollection);
        return entity;
    }

    @Override
    public void deleteById(long id)
    {
        if (id <= 0)
        {
            throw new IllegalArgumentException("Can't delete entity with id <= 0!");
        }

        List<T> rootCollection = rootCollectionProvider.apply(getRoot());
        rootCollection.removeIf(x -> Objects.equals(x.getId(), id));
        getManager().store(rootCollection);
    }

    //region SETTER / GETTER
    public void setEqualsPredicate(BiPredicate<T, T> equalsPredicate)
    {
        this.equalsPredicate = requireNonNull(equalsPredicate, "equalsPredicate can't be null!");
    }
    //endregion
}
