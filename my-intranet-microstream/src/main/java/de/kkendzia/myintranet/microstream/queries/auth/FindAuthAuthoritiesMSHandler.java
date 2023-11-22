package de.kkendzia.myintranet.microstream.queries.auth;

import de.kkendzia.myintranet.app.auth.queries.FindAuthAuthorities;
import de.kkendzia.myintranet.app.auth.queries.FindAuthAuthorities.Failure;
import de.kkendzia.myintranet.app.auth.queries.FindAuthAuthorities.FindAuthAuthoritiesHandler;
import de.kkendzia.myintranet.app.auth.shared.AuthAuthority;
import de.kkendzia.myintranet.domain.role.Role;
import de.kkendzia.myintranet.domain.role.RolePermission;
import de.kkendzia.myintranet.domain.user.EIUser;
import de.kkendzia.myintranet.microstream._core.MyIntranetRoot;
import de.kkendzia.myintranet.microstream._framework.AbstractMSQueryHandler;
import one.microstream.storage.types.StorageManager;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

import static de.kkendzia.myintranet.app._utils.Reduce.toOnlyElement;
import static java.util.Comparator.comparing;

@Component
public class FindAuthAuthoritiesMSHandler
        extends AbstractMSQueryHandler<FindAuthAuthorities, AuthAuthority, Failure>
        implements FindAuthAuthoritiesHandler
{
    public FindAuthAuthoritiesMSHandler(
            final MyIntranetRoot root,
            final StorageManager storageManager)
    {
        super(root, storageManager);
    }

    @Override
    public SingleQueryResult<AuthAuthority, Failure> fetchOne(final FindAuthAuthorities query)
    {
        final EIUser user = fetchUser(query);

        if (user == null)
        {
            return SingleQueryResult.failure(Failure.NO_USER);
        }

        // TODO: failure instead of exception?!
        return SingleQueryResult.success(
                fetchAuthorities(user)
                        .reduce(toOnlyElement(() -> new IllegalStateException("Found more than 1 Authority!")))
                        .orElseThrow(() -> new IllegalStateException("Found no Authority!")));
    }

    @Override
    public ListQueryResult<AuthAuthority, Failure> fetchAll(
            final FindAuthAuthorities query,
            Paging paging)
    {
        final EIUser user = fetchUser(query);

        if (user == null)
        {
            return ListQueryResult.failure(Failure.NO_USER);
        }

        final Stream<AuthAuthority> authorities =
                user
                        .getRoles()
                        .ids()
                        .stream()
                        .map(id -> getRoot().getRoles().get(id))
                        .sorted(comparing(Role::getName))
                        .flatMap(r -> Stream.concat(
                                Stream.of(r.getName()),
                                r.getPermissions()
                                        .stream()
                                        .map(this::getPermissionName)
                                        .sorted(String.CASE_INSENSITIVE_ORDER)))
                        .map(AuthAuthority::new);

        return map(authorities, paging);
    }


    private EIUser fetchUser(final FindAuthAuthorities query)
    {
        return
                getRoot()
                        .getEiUsers()
                        .get(query.userId());
    }

    private Stream<AuthAuthority> fetchAuthorities(final EIUser user)
    {
        return user
                .getRoles()
                .ids()
                .stream()
                .map(id -> getRoot().getRoles().get(id))
                .flatMap(r -> Stream.concat(
                        Stream.of(r.getName()),
                        r.getPermissions().stream().map(this::getPermissionName)
                ))
                .map(AuthAuthority::new);
    }

    private String getPermissionName(final RolePermission p)
    {
        return getRoot().getPermissions().get(p.getPermissionId()).getName();
    }
}
