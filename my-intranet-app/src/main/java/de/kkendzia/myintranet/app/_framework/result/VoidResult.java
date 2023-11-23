package de.kkendzia.myintranet.app._framework.result;

public interface VoidResult<F> extends Result<Void, F>
{
    //region STATIC
    static <F> VoidResult<F> success()
    {
        return new VoidResult<>()
        {
        };
    }

    static <F> VoidResult<F> failure(F failure)
    {
        return new VoidResult<>()
        {
            @Override
            public F getFailure()
            {
                return failure;
            }
        };
    }
    //endregion
}
