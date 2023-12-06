package de.kkendzia.myintranet.microstream.queries.auth;

import de.kkendzia.myintranet.app._framework.result.ListResult;
import de.kkendzia.myintranet.app.auth.queries.FindAuthAuthorities;
import de.kkendzia.myintranet.app.auth.queries.FindAuthAuthorities.Failure;
import de.kkendzia.myintranet.app.auth.queries.FindAuthAuthorities.FindAuthAuthoritiesHandler;
import de.kkendzia.myintranet.app.auth.shared.AuthAuthority;
import de.kkendzia.myintranet.domain.role.Role;
import de.kkendzia.myintranet.domain.role.RolePermission;
import de.kkendzia.myintranet.domain.user.EIUser;
import de.kkendzia.myintranet.microstream._framework.AbstractMSQueryHandler;
import one.microstream.storage.types.StorageManager;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;

@Component
public class FindAuthAuthoritiesMSHandler
        extends AbstractMSQueryHandler<EIUser>
        implements FindAuthAuthoritiesHandler
{
    public FindAuthAuthoritiesMSHandler(
            final StorageManager storageManager)
    {
        super(storageManager);
    }

    @Override
    public ListResult<AuthAuthority, Failure> fetchAll(final FindAuthAuthorities query)
    {
        final EIUser user = fetchUser(query);

        if (user == null)
        {
            return ListResult.failure(Failure.NO_USER);
        }

        final List<AuthAuthority> authorities =
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
                        .map(AuthAuthority::new)
                        .toList();

        return ListResult.success(authorities);
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
