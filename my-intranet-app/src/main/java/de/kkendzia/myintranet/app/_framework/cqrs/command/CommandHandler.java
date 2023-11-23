package de.kkendzia.myintranet.app._framework.cqrs.command;

import de.kkendzia.myintranet.app._framework.result.SingleResult;
import de.kkendzia.myintranet.app._framework.result.VoidResult;
import de.kkendzia.myintranet.domain._core.ID;

public interface CommandHandler<C extends CommandHandler.Command<F>, F>
{
    Class<C> getCommandClass();

    VoidResult<F> run(C command);

    //region TYPES
    interface Command<F>
    {

    }

    interface IDCommandHandler<C extends IDCommand<I, F>, I extends ID, F> extends CommandHandler<C, F>
    {
        @Override
        default VoidResult<F> run(C command)
        {
            return call(command).toVoid();
        }

        SingleResult<I, F> call(C command);
    }

    interface IDCommand<I extends ID, F> extends Command<F>
    {

    }
    //endregion
}
