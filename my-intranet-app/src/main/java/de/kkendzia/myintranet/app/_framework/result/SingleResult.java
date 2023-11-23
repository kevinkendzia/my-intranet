package de.kkendzia.myintranet.app._framework.result;

import java.util.Optional;

public interface SingleResult<R, F> extends Result<R, F>
{
    default Optional<R> asOptional()
    {
        return Optional.ofNullable(getData());
    }

    //region STATIC
    static <R, F> SingleResult<R, F> success(R data)
    {
        return new SingleResult<>()
        {
            @Override
            public R getData()
            {
                return data;
            }
        };
    }

    static <R, F> SingleResult<R, F> success()
    {
        return SingleResult.success(null);
    }

    static <R, F> SingleResult<R, F> failure(F failure)
    {
        return new SingleResult<>()
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
