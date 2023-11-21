package de.kkendzia.myintranet.app._framework;

import java.util.stream.Stream;

public interface SearchService<T, F extends SearchService.SearchFilters>
{
    int count(SearchQuery<F> query);

    Stream<T> fetch(SearchQuery<F> query);

    record SearchQuery<T extends SearchFilters>(
            T filters,
            Paging paging)
    {
    }

    interface SearchFilters
    {

    }
}
