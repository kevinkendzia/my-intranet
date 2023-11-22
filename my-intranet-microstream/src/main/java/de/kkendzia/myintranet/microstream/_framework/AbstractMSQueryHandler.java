package de.kkendzia.myintranet.microstream._framework;

import de.kkendzia.myintranet.app._framework.cqrs.QueryHandler;
import de.kkendzia.myintranet.app._framework.cqrs.QueryHandler.Query;
import de.kkendzia.myintranet.microstream._core.MyIntranetRoot;
import one.microstream.storage.types.StorageManager;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;
import static java.util.function.Function.identity;

public abstract class AbstractMSQueryHandler<Q extends Query<R, F>, R, F> implements QueryHandler<Q, R, F>
{
    private final MyIntranetRoot root;
    private final StorageManager storageManager;
    private Map<String, Comparator<R>> comparatorMap = new HashMap<>();

    protected AbstractMSQueryHandler(final MyIntranetRoot root, final StorageManager storageManager)
    {
        this.root = requireNonNull(root, "root can't be null!");
        this.storageManager = requireNonNull(storageManager, "storageManager can't be null!");
    }

    protected MyIntranetRoot getRoot()
    {
        return root;
    }

    protected StorageManager getStorageManager()
    {
        return storageManager;
    }

    protected void registerSortOrder(String key, Comparator<R> comparator)
    {
        comparatorMap.put(key, comparator);
    }

    protected Comparator<R> mapSortOrders(Collection<Order> orders)
    {
        return orders
                .stream().map(x ->
                {
                    final Comparator<R> c = comparatorMap.get(x.property());
                    if (x.direction() == Direction.DESC)
                    {
                        return c.reversed();
                    }
                    return c;
                })
                .reduce(Comparator::thenComparing)
                .orElseThrow();
    }

    protected ListQueryResult<R, F> map(Stream<R> results, Paging paging)
    {
        return fetch(results, paging, this::mapSortOrders);
    }

    protected <T> ListQueryResult<R, F> map(Stream<T> results, Paging paging, Function<T, R> mapper)
    {
        return fetch(results, paging, mapper, this::mapSortOrders);
    }

    //region STATIC
    protected static <T, T2, F> ListQueryResult<T2, F> fetch(
            Stream<T> items,
            Paging paging,
            Function<T, T2> mapper,
            Function<Collection<Order>, Comparator<T2>> orderMapper)
    {
        if (paging != null)
        {
            final Stream<T> pagedItems = items.skip(paging.offset()).limit(paging.limit());

            if (paging.orders() != null && !paging.orders().isEmpty())
            {
                return ListQueryResult.success(pagedItems
                        .map(mapper)
                        .sorted(orderMapper.apply(paging.orders()))
                        .toList());
            }
            return ListQueryResult.success(pagedItems
                    .map(mapper)
                    .toList());
        }
        return ListQueryResult.success(items.map(mapper).toList());
    }

    protected static <T, F> ListQueryResult<T, F> fetch(
            Stream<T> items,
            Paging<T> paging,
            Function<Collection<Order>, Comparator<T>> orderMapper)
    {
        return fetch(items, paging, identity(), orderMapper);
    }
    //endregion
}
