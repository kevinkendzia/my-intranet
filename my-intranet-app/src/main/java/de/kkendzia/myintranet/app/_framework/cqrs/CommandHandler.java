package de.kkendzia.myintranet.app._framework.cqrs;

public interface CommandHandler<C extends CommandHandler.Command>
{
    Class<C> getCommandClass();

    void execute(C command);

    interface Command {

    }
}
