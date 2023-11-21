package de.kkendzia.myintranet.app.search.user;

import de.kkendzia.myintranet.app._framework.SimpleSearchFilters;
import de.kkendzia.myintranet.app._framework.SimpleSearchItem;
import de.kkendzia.myintranet.domain.role.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

@Service
public class RoleDBSearchService implements RoleSearchService
{
    private final RoleRepository roleDAO;

    public RoleDBSearchService(RoleRepository roleDAO)
    {
        this.roleDAO = requireNonNull(roleDAO, "roleDAO can't be null!");
    }

    @Override
    public int count(SearchQuery<SimpleSearchFilters> query)
    {
        return Math.toIntExact(roleDAO.countAllByNameLike(query.filters().searchText()));
    }

    @Override
    public Stream<SimpleSearchItem> fetch(SearchQuery<SimpleSearchFilters> query)
    {
        return roleDAO.findAllByNameLike(query.filters().searchText(), query.paging())
                .map(u -> new SimpleSearchItem(u.getId(), u.getName()));
    }
}
