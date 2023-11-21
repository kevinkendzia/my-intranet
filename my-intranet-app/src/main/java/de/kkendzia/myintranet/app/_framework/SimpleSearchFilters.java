package de.kkendzia.myintranet.app._framework;

public record SimpleSearchFilters(String searchText) implements SearchService.SearchFilters
{
    // just a record
}
