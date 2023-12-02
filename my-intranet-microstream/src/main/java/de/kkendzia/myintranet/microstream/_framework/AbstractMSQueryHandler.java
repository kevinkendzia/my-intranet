package de.kkendzia.myintranet.microstream._framework;

import de.kkendzia.myintranet.app._framework.cqrs.query.paged.Direction;
import de.kkendzia.myintranet.app._framework.cqrs.query.paged.Order;
import de.kkendzia.myintranet.app._framework.cqrs.query.paged.Paging;
import org.eclipse.store.storage.types.StorageManager;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static de.kkendzia.myintranet.app._utils.StreamUtil.sortOptionally;
import static java.util.Objects.requireNonNull;

public class AbstractMSQueryHandler<T> extends StorageManagerHolder
{
    private final Map<String, Comparator<T>> comparatorMap = new HashMap<>();

    protected AbstractMSQueryHandler(final StorageManager storageManager)
    {
        super(storageManager);
    }

    protected void registerSortOrder(String key, Comparator<T> comparator)
    {
        comparatorMap.put(key, comparator);
    }

    protected Comparator<T> mapSortOrders(Collection<Order> orders)
    {
        if (orders == null)
        {
            return null;
        }

        return orders
                .stream()
                .map(x ->
                {
                    final Comparator<T> c = requireNonNull(
                            comparatorMap.get(x.property()),
                            "comparatorMap.get(x.property()) can't be null!");
                    if (x.direction() == Direction.DESC)
                    {
                        return c.reversed();
                    }
                    return c;
                })
                .reduce(Comparator::thenComparing)
                .orElse(null);
    }

    protected Stream<T> applyPaging(Stream<T> stream, Paging paging)
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
