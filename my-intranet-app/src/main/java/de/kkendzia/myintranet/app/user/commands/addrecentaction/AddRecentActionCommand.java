package de.kkendzia.myintranet.app.user.commands.addrecentaction;

import de.kkendzia.myintranet.app._framework.cqrs.CommandHandler;
import de.kkendzia.myintranet.app.user.shared.ActionItem;
import de.kkendzia.myintranet.domain.user.EIUser.EIUserID;

public record AddRecentActionCommand(EIUserID userId, ActionItem item) implements CommandHandler.Command
{
}
