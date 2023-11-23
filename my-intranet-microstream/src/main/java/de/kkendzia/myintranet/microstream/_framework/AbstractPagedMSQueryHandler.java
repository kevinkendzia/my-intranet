package de.kkendzia.myintranet.microstream._framework;

import de.kkendzia.myintranet.app._framework.cqrs.QueryHandler;
import de.kkendzia.myintranet.microstream._core.MyIntranetRoot;
import one.microstream.storage.types.StorageManager;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static de.kkendzia.myintranet.app._utils.StreamUtil.sortOptionally;

public class AbstractPagedMSQueryHandler<T> extends AbstractMSQueryHandler
{
    private final Map<String, Comparator<T>> comparatorMap = new HashMap<>();

    protected AbstractPagedMSQueryHandler(
            final MyIntranetRoot root,
            final StorageManager storageManager)
    {
        super(root, storageManager);
    }

    protected void registerSortOrder(String key, Comparator<T> comparator)
    {
        comparatorMap.put(key, comparator);
    }

    protected Comparator<T> mapSortOrders(Collection<QueryHandler.Order> orders)
    {
        if (orders == null)
        {
            return null;
        }

        return orders
                .stream().map(x ->
                {
                    final Comparator<T> c = comparatorMap.get(x.property());
                    if (x.direction() == QueryHandler.Direction.DESC)
                    {
                        return c.reversed();
                    }
                    return c;
                })
                .reduce(Comparator::thenComparing)
                .orElse(null);
    }

    protected Stream<T> applyPaging(Stream<T> stream, QueryHandler.Paging paging)
    {
        if (paging != null)
        {
            final Comparator<T> comparator = mapSortOrders(paging.orders());
            return sortOptionally(stream, comparator)
                    .skip(paging.offset())
                    .limit(paging.limit());
        }

        return stream;
    }
}
