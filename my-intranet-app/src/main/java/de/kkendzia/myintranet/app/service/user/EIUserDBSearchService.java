package de.kkendzia.myintranet.app.service.user;

import de.kkendzia.myintranet.domain.user.EIUserDAO;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

@Service
public class EIUserDBSearchService implements EIUserSearchService
{
    private final EIUserDAO userDAO;

    public EIUserDBSearchService(EIUserDAO userDAO)
    {
        this.userDAO = requireNonNull(userDAO, "userDAO can't be null!");
    }

    @Override
    public int count(SearchQuery<EIUserSearchFilters> query)
    {
        return Math.toIntExact(userDAO.countAllByUserNameLike(query.filters().searchText()));
    }

    @Override
    public Stream<EIUserSearchItem> fetch(SearchQuery<EIUserSearchFilters> query)
    {
        return userDAO.findAllByUserNameLike(query.filters().searchText(), query.paging())
                .map(u -> new EIUserSearchItem(u.getId(), u.getUserName()));
    }
}
