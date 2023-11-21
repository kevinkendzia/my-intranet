package de.kkendzia.myintranet.app.search.user;

import de.kkendzia.myintranet.app._framework.SimpleSearchFilters;
import de.kkendzia.myintranet.app._framework.SimpleSearchItem;
import de.kkendzia.myintranet.domain.permission.PermissionRepository;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

@Service
public class PermissionDBSearchService implements PermissionSearchService
{
    private final PermissionRepository permissionDAO;

    public PermissionDBSearchService(PermissionRepository permissionDAO)
    {
        this.permissionDAO = requireNonNull(permissionDAO, "permissionDAO can't be null!");
    }

    @Override
    public int count(SearchQuery<SimpleSearchFilters> query)
    {
        return Math.toIntExact(permissionDAO.countAllByNameLike(query.filters().searchText()));
    }

    @Override
    public Stream<SimpleSearchItem> fetch(SearchQuery<SimpleSearchFilters> query)
    {
        return permissionDAO.findAllByNameLike(query.filters().searchText(), query.paging())
                .map(u -> new SimpleSearchItem(u.getId(), u.getName()));
    }
}
