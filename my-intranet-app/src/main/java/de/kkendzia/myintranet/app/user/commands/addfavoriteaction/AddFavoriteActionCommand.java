package de.kkendzia.myintranet.app.user.commands.addfavoriteaction;

import de.kkendzia.myintranet.app._framework.cqrs.CommandHandler;
import de.kkendzia.myintranet.app.user.shared.ActionItem;
import de.kkendzia.myintranet.domain.user.EIUser;

public record AddFavoriteActionCommand(EIUser.EIUserID userId, ActionItem item) implements CommandHandler.Command
{
}
