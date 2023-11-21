package de.kkendzia.myintranet.app.search.mandant;

import de.kkendzia.myintranet.domain.mandant.MandantRepository;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
public class MandantDBSearchService implements MandantSearchService
{
    private final MandantRepository mandantDAO;

    public MandantDBSearchService(MandantRepository mandantDAO)
    {
        this.mandantDAO = mandantDAO;
    }

    @Override
    public int count(SearchQuery<MandantSearchFilters> query)
    {
        MandantSearchFilters filters = query.filters();
        return Math.toIntExact(mandantDAO.countByNameLike(filters.searchText()));
    }

    @Override
    public Stream<MandantSearchItem> fetch(SearchQuery<MandantSearchFilters> query)
    {
        return mandantDAO
                .findAllByNameLike(query.filters().searchText(), query.paging())
                .map(x -> new MandantSearchItem(x.getId(), x.getName()));
    }
}
