package de.kkendzia.myintranet.app.service.search.user;

import de.kkendzia.myintranet.domain.user.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Set;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toSet;

public class EIAuthService implements UserDetailsService
{
    private final EIUserDAO eiUserDAO;
    private final UserRoleDAO userRoleDAO;
    private final RoleDAO roleDAO;
    private final RolePermissionDAO rolePermissionDAO;
    private final PermissionDAO permissionDAO;

    public EIAuthService(
            EIUserDAO eiUserDAO,
            UserRoleDAO userRoleDAO,
            RoleDAO roleDAO,
            RolePermissionDAO rolePermissionDAO,
            PermissionDAO permissionDAO)
    {
        this.eiUserDAO = requireNonNull(eiUserDAO, "eiUserDAO can't be null!");
        this.userRoleDAO = requireNonNull(userRoleDAO, "userRoleDAO can't be null!");
        this.roleDAO = requireNonNull(roleDAO, "roleDAO can't be null!");
        this.rolePermissionDAO = requireNonNull(rolePermissionDAO, "rolePermissionDAO can't be null!");
        this.permissionDAO = requireNonNull(permissionDAO, "permissionDAO can't be null!");
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        EIUser u = eiUserDAO
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Couldn't find user for username \"" + username + "\""));

        Set<GrantedAuthority> authorities =
                userRoleDAO
                        .findAllByUserId(u.getId())
                        .map(ur -> roleDAO.findById(ur.getRoleId()))
                        .flatMap(r -> Stream.concat(
                                Stream.of(mapRole(r)),
                                loadPermissionAuthorities(r)))
                        .collect(toSet());

        return new User(u.getUserName(), u.getPassword(), authorities);
    }

    private Stream<SimpleGrantedAuthority> loadPermissionAuthorities(Role r)
    {
        return rolePermissionDAO.findAllByRoleId(r.getId())
                .map(rp -> permissionDAO.findById(rp.getPermissionId()))
                .map(EIAuthService::mapPermission);
    }

    private static SimpleGrantedAuthority mapPermission(Permission permission)
    {
        return new SimpleGrantedAuthority("ROLE_" + permission.getName());
    }

    private static SimpleGrantedAuthority mapRole(Role role)
    {
        return new SimpleGrantedAuthority("ROLE_" + role.getName());
    }
}
