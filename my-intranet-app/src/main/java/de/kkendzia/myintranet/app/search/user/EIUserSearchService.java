package de.kkendzia.myintranet.app.search.user;

import de.kkendzia.myintranet.app._framework.SearchService;

public interface EIUserSearchService
        extends SearchService<EIUserSearchService.EIUserSearchItem, EIUserSearchService.EIUserSearchFilters>
{
    record EIUserSearchItem(
            long id,
            String name)
    {
        // just a record
    }

    record EIUserSearchFilters(String searchText) implements SearchFilters
    {
        // just a record
    }
}
