package de.kkendzia.myintranet.ei.core.session;

import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import com.vaadin.flow.spring.security.AuthenticationContext;
import de.kkendzia.myintranet.app._framework.cqrs.query.QueryMediator;
import de.kkendzia.myintranet.app.useractions.queries.FindUserIDByUsername;
import de.kkendzia.myintranet.domain.user.EIUser.EIUserID;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@VaadinSessionScope
public class EISession implements Serializable
{
    private final transient AuthenticationContext context;
    private final transient QueryMediator queryMediator;

    // STATE
    private SessionUser user;

    public EISession(
            final AuthenticationContext context,
            final QueryMediator queryMediator)
    {
        this.context = context;
        this.queryMediator = queryMediator;
    }

    public SessionUser user()
    {
        if (user == null)
        {
            final var authUser = context
                    .getAuthenticatedUser(User.class)
                    .orElseThrow(() -> new IllegalStateException("No User-Principal set in SecurityContext!"));
            final EIUserID userId = queryMediator.fetchOne(new FindUserIDByUsername(authUser.getUsername())).getData();
            this.user = new SessionUser(userId, authUser.getUsername());
        }
        return user;
    }

    public EIUserID userId()
    {
        return user().id();
    }

    public String username()
    {
        return user().username();
    }

    public void logout()
    {
        context.logout();
    }

    public record SessionUser(
            EIUserID id,
            String username) implements Serializable
    {
        // just a record
    }
}
