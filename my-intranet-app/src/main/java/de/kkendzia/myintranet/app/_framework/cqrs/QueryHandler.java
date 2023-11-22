package de.kkendzia.myintranet.app._framework.cqrs;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static de.kkendzia.myintranet.app._utils.Reduce.toOnlyElement;

public interface QueryHandler<Q extends QueryHandler.Query<R, F>, R, F>
{
    Class<Q> getQueryClass();

    default SingleQueryResult<R, F> fetchOne(Q query)
    {
        return fetchAll(query).reduce(() -> new IllegalStateException("Found more then 1 item!"));
    }

    default long count(Q query)
    {
        return fetchAll(query).count();
    }

    ListQueryResult<R, F> fetchAll(Q query, Paging paging);

    default ListQueryResult<R, F> fetchAll(Q query)
    {
        return fetchAll(query, null);
    }

    //region TYPES
    interface Query<R, F>
    {
    }

    record Paging(
            int offset,
            int limit,
            List<Order> orders)
    {
    }

    record Order(
            String property,
            Direction direction)
    {
        // just record
    }

    enum Direction
    {
        ASC,
        DESC;
    }

    interface QueryResult<R, F>
    {
        default F getFailure()
        {
            return null;
        }

        default boolean isSuccess()
        {
            return getFailure() == null;
        }

        default R getData()
        {
            return null;
        }
    }

    interface SingleQueryResult<R, F> extends QueryResult<R, F>
    {
        default Optional<R> asOptional()
        {
            return Optional.ofNullable(getData());
        }

        //region STATIC
        static <R, F> SingleQueryResult<R, F> success(R data)
        {
            return new SingleQueryResult<>()
            {
                @Override
                public R getData()
                {
                    return data;
                }
            };
        }

        static <R, F> SingleQueryResult<R, F> success()
        {
            return SingleQueryResult.success(null);
        }

        static <R, F> SingleQueryResult<R, F> failure(F failure)
        {
            return new SingleQueryResult<>()
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

    interface ListQueryResult<R, F> extends QueryResult<List<R>, F>
    {
        default Stream<R> stream()
        {
            return getData().stream();
        }

        default long count()
        {
            return getData().size();
        }

        default Stream<R> orElseEmpty()
        {
            if (isSuccess())
            {
                return getData().stream();
            }
            return Stream.empty();
        }

        default SingleQueryResult<R, F> first()
        {
            return isSuccess()
                   ? SingleQueryResult.success(stream().findFirst().orElse(null))
                   : SingleQueryResult.failure(getFailure());
        }

        default SingleQueryResult<R, F> reduce(Supplier<RuntimeException> throwableSupplier)
        {
            return isSuccess()
                   ? SingleQueryResult.success(stream().reduce(toOnlyElement(throwableSupplier)).orElse(null))
                   : SingleQueryResult.failure(getFailure());
        }

        //region STATIC
        static <R, F> ListQueryResult<R, F> success(List<R> data)
        {
            return new ListQueryResult<>()
            {
                @Override
                public List<R> getData()
                {
                    return data;
                }
            };
        }

        static <R, F> ListQueryResult<R, F> success()
        {
            return success(null);
        }

        static <R, F> ListQueryResult<R, F> failure(F failure)
        {
            return new ListQueryResult<>()
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
    //endregion
}
