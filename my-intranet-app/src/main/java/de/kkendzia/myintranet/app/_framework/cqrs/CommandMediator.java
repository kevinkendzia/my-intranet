package de.kkendzia.myintranet.app._framework.cqrs;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

import static java.util.Objects.requireNonNull;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@Service
public class CommandMediator
{
    private final Map<Class<? extends CommandHandler.Command<?>>, CommandHandler<?, ?>> commandHandlerMap;

    public CommandMediator(final Set<CommandHandler<?, ?>> commandHandlers)
    {
        requireNonNull(commandHandlers, "commandHandlers can't be null!");
        this.commandHandlerMap = commandHandlers.stream().collect(toMap(CommandHandler::getCommandClass, identity()));
    }

    public <C extends CommandHandler.Command<F>, F> void execute(C command)
    {
        executeResult(command);
    }

    @SuppressWarnings("unchecked")
    public <C extends CommandHandler.Command<F>, F> CommandHandler.CommandResult<F> executeResult(C command)
    {
        final CommandHandler<C, F> handler = (CommandHandler<C, F>) commandHandlerMap.get(command.getClass());
        return handler.executeResult(command);
    }
}
