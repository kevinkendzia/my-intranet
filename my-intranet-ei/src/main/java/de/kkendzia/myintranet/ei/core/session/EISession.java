package de.kkendzia.myintranet.ei.core.session;

import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import com.vaadin.flow.spring.security.AuthenticationContext;
import de.kkendzia.myintranet.app._framework.cqrs.CommandMediator;
import de.kkendzia.myintranet.app._framework.cqrs.QueryMediator;
import de.kkendzia.myintranet.app.useractions.commands.AddFavoriteAction;
import de.kkendzia.myintranet.app.useractions.commands.AddRecentAction;
import de.kkendzia.myintranet.app.useractions.queries.FindFavoriteActions;
import de.kkendzia.myintranet.app.useractions.queries.FindRecentActions;
import de.kkendzia.myintranet.app.useractions.queries.FindUserIDByUsername;
import de.kkendzia.myintranet.app.useractions.shared.ActionItem;
import de.kkendzia.myintranet.domain.user.EIUser;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

import static java.util.Collections.unmodifiableList;

@Component
@VaadinSessionScope
public class EISession implements Serializable
{
    private final transient AuthenticationContext context;
    private final QueryMediator queryMediator;
    private final CommandMediator commandMediator;

    // STATE
    private EIUser.EIUserID userId;

    public EISession(
            final AuthenticationContext context,
            final QueryMediator queryMediator,
            final CommandMediator commandMediator)
    {
        this.context = context;
        this.queryMediator = queryMediator;
        this.commandMediator = commandMediator;
    }

    private EIUser.EIUserID getCurrentUserID()
    {
        if (userId == null)
        {
            final var u = context
                    .getAuthenticatedUser(User.class)
                    .orElseThrow(() -> new IllegalStateException("No User-Principal set in SecurityContext!"));
            userId = queryMediator.execute(new FindUserIDByUsername(u.getUsername()));
        }
        return userId;
    }

    public String getUserName()
    {
        return context.getPrincipalName().orElse("UNKNOWN");
    }

    public List<ActionItem> getFavoriteActions()
    {
        return unmodifiableList(queryMediator.execute(new FindFavoriteActions(getCurrentUserID(), 5)));
    }

    public void addFavoriteAction(ActionItem action)
    {
        commandMediator.execute(new AddFavoriteAction(getCurrentUserID(), action));
    }

    public List<ActionItem> getPreviousActions()
    {
        return unmodifiableList(queryMediator.execute(new FindRecentActions(getCurrentUserID(), 5)));
    }

    public void addPreviousAction(ActionItem action)
    {
        commandMediator.execute(new AddRecentAction(getCurrentUserID(), action));
    }

    public void logout()
    {
        context.logout();
    }
}
