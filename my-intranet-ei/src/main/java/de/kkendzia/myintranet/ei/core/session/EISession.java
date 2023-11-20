package de.kkendzia.myintranet.ei.core.session;

import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import com.vaadin.flow.spring.security.AuthenticationContext;
import de.kkendzia.myintranet.app.service.identity.UserService;
import de.kkendzia.myintranet.domain.user.EIUser;
import de.kkendzia.myintranet.domain.user.EIUserAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

import static java.util.Collections.unmodifiableList;

//   TODO: Needs work!
@Component
@VaadinSessionScope
public class EISession implements Serializable
{
    @Autowired
    private AuthenticationContext context;

    @Autowired
    private UserService userService;

    private EIUser user;

    private EIUser getCurrentUser()
    {
        if (user == null)
        {
            final var u = context.getAuthenticatedUser(User.class)
                    .orElseThrow(() -> new IllegalStateException("No User-Principal set in SecurityContext!"));
            user = userService.loadUserByUsername(u.getUsername());
        }
        return user;
    }

    public String getUserName()
    {
        return getCurrentUser().getUserName();
    }

    public List<EIUserAction> getFavoriteActions()
    {
        return unmodifiableList(getCurrentUser().getFavoriteActions());
    }

    public void addFavoriteAction(EIUserAction action)
    {
        userService.addFavoriteUserAction(action);
    }

    public List<EIUserAction> getPreviousActions()
    {
        return unmodifiableList(user.getPreviousActions());
    }

    public void addPreviousAction(EIUserAction action)
    {
        userService.addPreviousUserAction(action);
    }

    public void logout()
    {
        context.logout();
    }

    public record HomeAction(String route)
    {

    }
}
