package de.kkendzia.myintranet.app.init.commands;

import de.kkendzia.myintranet.app._framework.cqrs.command.CommandHandler;

public record SetAppInit(boolean init) implements CommandHandler.Command<Void>
{
    public interface SetAppInitHandler extends CommandHandler<SetAppInit, Void>
    {
        @Override
        default Class<SetAppInit> getCommandClass()
        {
            return SetAppInit.class;
        }
    }
}
