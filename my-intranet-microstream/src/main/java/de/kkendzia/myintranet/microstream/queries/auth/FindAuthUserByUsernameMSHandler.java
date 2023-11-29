package de.kkendzia.myintranet.microstream.queries.auth;

import de.kkendzia.myintranet.app._framework.result.SingleResult;
import de.kkendzia.myintranet.app._utils.Reduce;
import de.kkendzia.myintranet.app.auth.queries.FindAuthUserByUsername;
import de.kkendzia.myintranet.app.auth.queries.FindAuthUserByUsername.Failure;
import de.kkendzia.myintranet.app.auth.queries.FindAuthUserByUsername.FindAuthUserByUsernameHandler;
import de.kkendzia.myintranet.app.auth.shared.AuthUser;
import de.kkendzia.myintranet.domain.user.EIUser;
import de.kkendzia.myintranet.microstream._framework.AbstractPagedMSQueryHandler;
import one.microstream.storage.types.StorageManager;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;

@Component
public class FindAuthUserByUsernameMSHandler
        extends AbstractPagedMSQueryHandler<EIUser>
        implements FindAuthUserByUsernameHandler
{
    public FindAuthUserByUsernameMSHandler(
            final StorageManager storageManager)
    {
        super(storageManager);
        registerSortOrder("username", comparing(EIUser::getUserName));
    }

    @Override
    public SingleResult<AuthUser, Failure> fetchOne(final FindAuthUserByUsername query)
    {
        return fetchUsers(query)
                .reduce(Reduce.toOnlyElement())
                .map(this::mapUser)
                .map(SingleResult::<AuthUser, Failure>success)
                .orElseGet(() -> SingleResult.failure(Failure.NO_USER));
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
