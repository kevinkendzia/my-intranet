package de.kkendzia.myintranet.ei.core.security;

import com.vaadin.flow.spring.security.VaadinWebSecurity;
import de.kkendzia.myintranet.app.service.search.user.EIAuthService;
import de.kkendzia.myintranet.domain.user.*;
import de.kkendzia.myintranet.ei.ui.views.login.LoginView;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * see https://www.baeldung.com/role-and-privilege-for-spring-security-registration
 */
@EnableWebSecurity
@Configuration
public class EISecurityConfiguration
        extends VaadinWebSecurity
{
    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        // Delegating the responsibility of general configurations
        // of http security to the super class. It's configuring
        // the followings: Vaadin's CSRF protection by ignoring
        // framework's internal requests, default request cache,
        // ignoring public views annotated with @AnonymousAllowed,
        // restricting access to other views/endpoints, and enabling
        // ViewAccessChecker authorization.
        // You can add any possible extra configurations of your own
        // here (the following is just an example):

        // http.rememberMe().alwaysRemember(false);

        // Configure your static resources with public access before calling
        // super.configure(HttpSecurity) as it adds final anyRequest matcher
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(new AntPathRequestMatcher("/public/**"))
                .permitAll());

        super.configure(http);

        // This is important to register your login view to the
        // view access checker mechanism:
        setLoginView(http, LoginView.class);
    }

    @Override
    public void configure(WebSecurity web) throws Exception
    {
        // Customize your WebSecurity configuration.
        super.configure(web);
    }

    @Bean
    public UserDetailsService userDetailsService(
            EIUserDAO eiUserDAO,
            UserRoleDAO userRoleDAO,
            RoleDAO roleDAO,
            RolePermissionDAO rolePermissionDAO,
            PermissionDAO permissionDAO)
    {
        return new EIAuthService(eiUserDAO, userRoleDAO, roleDAO, rolePermissionDAO, permissionDAO);
    }

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        // 22.10.2023 KK TODO:
        return NoOpPasswordEncoder.getInstance();
    }
}
