package de.kkendzia.myintranet.app._framework.cqrs.command;

import de.kkendzia.myintranet.app._framework.cqrs.command.CommandHandler.Command;
import de.kkendzia.myintranet.app._framework.cqrs.command.CommandHandler.IDCommand;
import de.kkendzia.myintranet.app._framework.result.SingleResult;
import de.kkendzia.myintranet.app._framework.result.VoidResult;
import de.kkendzia.myintranet.domain._core.elements.ID;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static java.util.Objects.requireNonNull;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@Service
public class CommandMediator
{
    private final Map<Class<? extends Command<?>>, CommandHandler<?, ?>> commandHandlerMap;

    public CommandMediator(final Set<CommandHandler<?, ?>> commandHandlers)
    {
        requireNonNull(commandHandlers, "commandHandlers can't be null!");
        this.commandHandlerMap = commandHandlers.stream().collect(toMap(CommandHandler::getCommandClass, identity()));
    }

    @SuppressWarnings("unchecked")
    public <C extends Command<F>, F> VoidResult<F> run(C command)
    {
        final CommandHandler<C, F> handler = (CommandHandler<C, F>) Optional
                .ofNullable(commandHandlerMap.get(command.getClass()))
                .orElseThrow(() -> new IllegalStateException("CommandHandler for \"" + command.getClass() + "\" wasn't implemented yet!"));
        return handler.run(command);
    }

    @SuppressWarnings("unchecked")
    public <C extends IDCommand<I, F>, I extends ID, F> SingleResult<I, F> call(C command)
    {
        final CommandHandler.IDCommandHandler<C, I, F> handler = (CommandHandler.IDCommandHandler<C, I, F>) Optional
                .ofNullable(commandHandlerMap.get(command.getClass()))
                .orElseThrow(() -> new IllegalStateException("CommandHandler for \"" + command.getClass() + "\" wasn't implemented yet!"));
        return handler.call(command);
    }
}
