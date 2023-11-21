package de.kkendzia.myintranet.ei.core.session;

import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import com.vaadin.flow.spring.security.AuthenticationContext;
import de.kkendzia.myintranet.app._framework.cqrs.CommandMediator;
import de.kkendzia.myintranet.app._framework.cqrs.QueryMediator;
import de.kkendzia.myintranet.app.user.commands.addfavoriteaction.AddFavoriteActionCommand;
import de.kkendzia.myintranet.app.user.commands.addrecentaction.AddRecentActionCommand;
import de.kkendzia.myintranet.app.user.queries.favoriteactions.FavoriteActionsQuery;
import de.kkendzia.myintranet.app.user.queries.recentactions.RecentActionsQuery;
import de.kkendzia.myintranet.app.user.queries.user.EIUserIDByUserNameQuery;
import de.kkendzia.myintranet.app.user.shared.ActionItem;
import de.kkendzia.myintranet.domain.user.EIUser;
import de.kkendzia.myintranet.domain.user.EIUserAction;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

import static java.util.Collections.unmodifiableList;

@Component
@VaadinSessionScope
public class EISession implements Serializable
{
    private final AuthenticationContext context;
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
            userId = queryMediator.execute(new EIUserIDByUserNameQuery(u.getUsername()));
        }
        return userId;
    }

    public String getUserName()
    {
        return context.getPrincipalName().orElse("UNKNOWN");
    }

    public List<ActionItem> getFavoriteActions()
    {
        return unmodifiableList(queryMediator.execute(new FavoriteActionsQuery(getCurrentUserID(), 5)));
    }

    public void addFavoriteAction(ActionItem action)
    {
        commandMediator.execute(new AddFavoriteActionCommand(getCurrentUserID(), action));
    }

    public List<ActionItem> getPreviousActions()
    {
        return unmodifiableList(queryMediator.execute(new RecentActionsQuery(getCurrentUserID(), 5)));
    }

    public void addPreviousAction(ActionItem action)
    {
        commandMediator.execute(new AddRecentActionCommand(getCurrentUserID(), action));
    }

    public void logout()
    {
        context.logout();
    }
}
