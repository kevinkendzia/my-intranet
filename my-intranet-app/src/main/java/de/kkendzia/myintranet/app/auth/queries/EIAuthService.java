package de.kkendzia.myintranet.app.auth.queries;

import de.kkendzia.myintranet.app._framework.cqrs.QueryHandler;
import de.kkendzia.myintranet.app._framework.cqrs.QueryHandler.ListQueryResult;
import de.kkendzia.myintranet.app._framework.cqrs.QueryHandler.SingleQueryResult;
import de.kkendzia.myintranet.app._framework.cqrs.QueryMediator;
import de.kkendzia.myintranet.app.auth.queries.FindAuthAuthorities.Failure;
import de.kkendzia.myintranet.app.auth.shared.AuthAuthority;
import de.kkendzia.myintranet.app.auth.shared.AuthUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;
import java.util.Set;

import static java.util.Collections.emptySet;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toSet;

public class EIAuthService implements UserDetailsService
{
    private final QueryMediator queryMediator;

    public EIAuthService(final QueryMediator queryMediator)
    {
        this.queryMediator = requireNonNull(queryMediator, "queryMediator can't be null!");
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        // 19.11.2023 KK Only load needed data! (userName + password)
        final SingleQueryResult<AuthUser, FindAuthUserByUsername.Failure> userResult =
                queryMediator.fetchOne(new FindAuthUserByUsername(username));

        if (userResult.isSuccess())
        {
            final AuthUser user =
                    userResult
                            .asOptional()
                            .orElseThrow(() -> new UsernameNotFoundException("Couldn't find user for userName \"" + username + "\"!"));

            final ListQueryResult<AuthAuthority, Failure> authorityResult = queryMediator.fetchAll(new FindAuthAuthorities(user.id()));
            if (authorityResult.isSuccess())
            {
                Set<GrantedAuthority> authorities = mapAuthorities(authorityResult);
                return new User(user.username(), user.password(), authorities);
            }

            return new User(user.username(), user.password(), emptySet());
        }
        else
        {
            throw new UsernameNotFoundException("Couldn't find user for userName \"" + username + "\"!");
        }
    }

    private static Set<GrantedAuthority> mapAuthorities(final ListQueryResult<AuthAuthority, Failure> result)
    {
        return
                result
                        .stream()
                        .map(x -> new SimpleGrantedAuthority("ROLE_" + x.name()))
                        .collect(toSet());
    }
}
