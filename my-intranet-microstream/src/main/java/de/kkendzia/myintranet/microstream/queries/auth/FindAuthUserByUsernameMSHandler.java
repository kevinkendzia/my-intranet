package de.kkendzia.myintranet.microstream.queries.auth;

import de.kkendzia.myintranet.app._framework.cqrs.QueryHandler;
import de.kkendzia.myintranet.app._framework.cqrs.QueryHandler.ListQueryResult;
import de.kkendzia.myintranet.app._framework.cqrs.QueryHandler.Paging;
import de.kkendzia.myintranet.app.auth.queries.FindAuthUserByUsername;
import de.kkendzia.myintranet.app.auth.queries.FindAuthUserByUsername.Failure;
import de.kkendzia.myintranet.app.auth.queries.FindAuthUserByUsername.FindAuthUserByUsernameHandler;
import de.kkendzia.myintranet.app.auth.shared.AuthUser;
import de.kkendzia.myintranet.domain.user.EIUser;
import de.kkendzia.myintranet.microstream._core.MyIntranetRoot;
import de.kkendzia.myintranet.microstream._framework.AbstractMSQueryHandler;
import de.kkendzia.myintranet.microstream._framework.AbstractPagedMSQueryHandler;
import one.microstream.storage.types.StorageManager;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static de.kkendzia.myintranet.app._utils.Reduce.toOnlyElement;
import static java.util.Comparator.comparing;

@Component
public class FindAuthUserByUsernameMSHandler
        extends AbstractPagedMSQueryHandler<EIUser>
        implements FindAuthUserByUsernameHandler
{
    public FindAuthUserByUsernameMSHandler(
            final MyIntranetRoot root,
            final StorageManager storageManager)
    {
        super(root, storageManager);
        registerSortOrder("username", comparing(EIUser::getUserName));
    }

    @Override
    public ListQueryResult<AuthUser, Failure> fetchAll(
            final FindAuthUserByUsername query,
            final Paging paging)
    {
        final List<AuthUser> result =
                applyPaging(fetchUsers(query), paging)
                        .map(this::mapUser)
                        .toList();

        return ListQueryResult.success(result);
    }

    private AuthUser mapUser(final EIUser u)
    {
        return new AuthUser(u.getId(), u.getUserName(), u.getPassword());
    }

    private Stream<EIUser> fetchUsers(final FindAuthUserByUsername query)
    {
        return getRoot()
                .getEiUsers()
                .values()
                .stream()
                .filter(u -> Objects.equals(u.getUserName(), query.username()));
    }
}
