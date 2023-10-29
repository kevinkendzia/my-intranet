package de.kkendzia.myintranet.app.service.search.mandant;

import de.kkendzia.myintranet.app.service._framework.SearchService;

public interface MandantSearchService extends SearchService<MandantSearchService.MandantSearchItem, MandantSearchService.MandantSearchFilters>
{
    record MandantSearchFilters(String searchText) implements SearchService.SearchFilters
    {
        // just a record
    }

    record MandantSearchItem(
            long id,
            String name)
    {
        // just a record
    }
}
