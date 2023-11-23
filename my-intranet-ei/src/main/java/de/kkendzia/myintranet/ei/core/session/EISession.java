package de.kkendzia.myintranet.ei.core.session;

import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import com.vaadin.flow.spring.security.AuthenticationContext;
import de.kkendzia.myintranet.app._framework.cqrs.command.CommandMediator;
import de.kkendzia.myintranet.app._framework.cqrs.query.QueryMediator;
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

import static de.kkendzia.myintranet.app._framework.cqrs.query.paged.Paging.firstPage;
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
            userId = queryMediator.fetchOne(new FindUserIDByUsername(u.getUsername())).getData();
        }
        return userId;
    }

    public String getUserName()
    {
        return context.getPrincipalName().orElse("UNKNOWN");
    }

    public List<ActionItem> getFavoriteActions()
    {
        return unmodifiableList(queryMediator.fetchAll(new FindFavoriteActions(getCurrentUserID()), firstPage(5))
                .toList());
    }

    public void addFavoriteAction(ActionItem action)
    {
        commandMediator.execute(new AddFavoriteAction(getCurrentUserID(), action));
    }

    public List<ActionItem> getPreviousActions()
    {
        return unmodifiableList(queryMediator.fetchAll(new FindRecentActions(getCurrentUserID()), firstPage(5))
                .toList());
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
