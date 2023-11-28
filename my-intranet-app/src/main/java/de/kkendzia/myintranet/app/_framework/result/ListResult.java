package de.kkendzia.myintranet.app._framework.result;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static de.kkendzia.myintranet.app._utils.Reduce.toOnlyElement;

public interface ListResult<R, F> extends Result<List<R>, F>
{
    default Stream<R> stream()
    {
        return getData().stream();
    }

    default List<R> toList()
    {
        return stream().toList();
    }

    default VoidResult<F> toVoid()
    {
        return isSuccess() ? VoidResult.success() : VoidResult.failure(getFailure());
    }

    default long count()
    {
        return getData().size();
    }

    default SingleResult<R, F> first()
    {
        return isSuccess()
               ? SingleResult.success(stream().findFirst().orElse(null))
               : SingleResult.failure(getFailure());
    }

    default SingleResult<R, F> reduce(Supplier<RuntimeException> throwableSupplier)
    {
        return isSuccess()
               ? SingleResult.success(stream().reduce(toOnlyElement(throwableSupplier)).orElse(null))
               : SingleResult.failure(getFailure());
    }

    //region STATIC
    static <R, F> ListResult<R, F> success(List<R> data)
    {
        return new ListResult<>()
        {
            @Override
            public List<R> getData()
            {
                return data;
            }
        };
    }

    static <R, F> ListResult<R, F> success(R data)
    {
        return success(List.of(data));
    }

    static <R, F> ListResult<R, F> success()
    {
        return success(null);
    }

    static <R, F> ListResult<R, F> failure(F failure)
    {
        return new ListResult<>()
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
