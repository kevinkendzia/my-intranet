package de.kkendzia.myintranet.app._framework;

import java.util.List;

import static java.util.Objects.requireNonNull;

public record Paging(
        int offset,
        int limit,
        List<SortOrder> sortOrders)
{
    public Paging
    {
        requireNonNull(sortOrders, "sortOrders can't be null!");
    }

    public record SortOrder(
            String property,
            boolean ascending)
    {
        // just a record
    }
}
