package de.kkendzia.myintranet.microstream.queries.auth;

import de.kkendzia.myintranet.app.auth.queries.FindAuthUserByUsername;
import de.kkendzia.myintranet.app.auth.queries.FindAuthUserByUsername.Failure;
import de.kkendzia.myintranet.app.auth.queries.FindAuthUserByUsername.FindAuthUserByUsernameHandler;
import de.kkendzia.myintranet.app.auth.shared.AuthUser;
import de.kkendzia.myintranet.domain.user.EIUser;
import de.kkendzia.myintranet.microstream._core.MyIntranetRoot;
import de.kkendzia.myintranet.microstream._framework.AbstractMSQueryHandler;
import one.microstream.storage.types.StorageManager;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Stream;

import static de.kkendzia.myintranet.app._utils.Reduce.toOnlyElement;

@Component
public class FindAuthUserByUsernameMSHandler
        extends AbstractMSQueryHandler<FindAuthUserByUsername, AuthUser, Failure>
        implements FindAuthUserByUsernameHandler
{
    public FindAuthUserByUsernameMSHandler(
            final MyIntranetRoot root,
            final StorageManager storageManager)
    {
        super(root, storageManager);
    }

    @Override
    public ListQueryResult<AuthUser, Failure> fetchAll(
            final FindAuthUserByUsername query,
            final Paging paging)
    {
        return map(fetchUsers(query), x -> , paging);

        if (paging != null)
        {
            requireNoOrders(paging);
            if (paging.comparator() != null)
            {
                return ListQueryResult.success(fetchUsers(query)
                        .skip(paging.offset())
                        .limit(paging.limit())
                        .map(FindAuthUserByUsernameMSHandler::mapUser)
                        .sorted(paging.comparator())
                        .toList());
            }
            return ListQueryResult.success(fetchUsers(query)
                    .skip(paging.offset())
                    .limit(paging.limit())
                    .map(FindAuthUserByUsernameMSHandler::mapUser)
                    .toList());
        }
        return ListQueryResult.success(fetchUsers(query).map(FindAuthUserByUsernameMSHandler::mapUser).toList());
    }

    private static AuthUser mapUser(final EIUser u)
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
