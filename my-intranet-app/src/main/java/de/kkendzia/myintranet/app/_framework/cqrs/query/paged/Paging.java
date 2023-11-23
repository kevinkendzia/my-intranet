package de.kkendzia.myintranet.app._framework.cqrs.query.paged;

import java.util.List;

import static java.util.Collections.emptyList;

public record Paging(
        int offset,
        int limit,
        List<Order> orders)
{
    public static Paging firstPage(int limit, List<Order> orders)
    {
        return new Paging(0, limit, orders);
    }

    public static Paging firstPage(int limit)
    {
        return firstPage(limit, emptyList());
    }

    public static Paging allSorted(List<Order> orders)
    {
        return new Paging(0, Integer.MAX_VALUE, orders);
    }
}
