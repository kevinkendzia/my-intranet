package de.kkendzia.myintranet.app._framework.result;

public interface Result<R, F>
{
    default boolean isSuccess()
    {
        return getFailure() == null;
    }

    default F getFailure()
    {
        return null;
    }

    default R getData()
    {
        return null;
    }

    default VoidResult<F> toVoid()
    {
        return isSuccess() ? VoidResult.success() : VoidResult.failure(getFailure());
    }
}
