package de.kkendzia.myintranet.ei.utils;

import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.provider.QuerySortOrder;
import com.vaadin.flow.data.provider.SortDirection;
import de.kkendzia.myintranet.app._framework.cqrs.query.paged.Direction;
import de.kkendzia.myintranet.app._framework.cqrs.query.paged.Order;
import de.kkendzia.myintranet.app._framework.cqrs.query.paged.Paging;

import java.util.List;

public class QueryUtils
{
    private QueryUtils()
    {
        // No Instance!
    }

    public static Paging mapPaging(Query<?, ?> query)
    {
        return new Paging(
                query.getOffset(),
                query.getLimit(),
                mapSortOrders(query.getSortOrders()));
    }

    public static List<Order> mapSortOrders(final List<QuerySortOrder> sortOrders)
    {
        return sortOrders
                .stream()
                .map(x -> new Order(
                        x.getSorted(),
                        x.getDirection() == SortDirection.ASCENDING
                        ? Direction.ASC
                        : Direction.DESC))
                .toList();
    }
}
