package de.kkendzia.myintranet.app.search.user;

import de.kkendzia.myintranet.app._framework.SearchService;
import de.kkendzia.myintranet.domain.user.EIUserRepository;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

@Service
public class EIUserDBSearchService implements EIUserSearchService
{
    private final EIUserRepository userDAO;

    public EIUserDBSearchService(EIUserRepository userDAO)
    {
        this.userDAO = requireNonNull(userDAO, "userDAO can't be null!");
    }

    @Override
    public int count(SearchService.SearchQuery<EIUserSearchFilters> query)
    {
        return Math.toIntExact(userDAO.countAllByUserNameLike(query.filters().searchText()));
    }

    @Override
    public Stream<EIUserSearchItem> fetch(SearchService.SearchQuery<EIUserSearchFilters> query)
    {
        return userDAO.findAllByUserNameLike(query.filters().searchText(), query.paging())
                .map(u -> new EIUserSearchItem(u.getId(), u.getUserName()));
    }
}
