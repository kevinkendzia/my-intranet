package de.kkendzia.myintranet.app._framework.cqrs;

public interface CommandHandler<C extends CommandHandler.Command<F>, F>
{
    Class<C> getCommandClass();

    default void execute(C command)
    {
        executeResult(command);
    }

    CommandResult<F> executeResult(C command);

    interface Command<F> {

    }

    interface CommandResult<F>
    {
        default F getFailure()
        {
            return null;
        }

        default boolean isSuccess()
        {
            return getFailure() == null;
        }

        static <F> CommandResult<F> success()
        {
            return new CommandResult<F>()
            {

            };
        }

        static <F> CommandResult<F> failure(F failure)
        {
            return new CommandResult<>()
            {
                @Override
                public F getFailure()
                {
                    return failure;
                }
            };
        }
    }
}
