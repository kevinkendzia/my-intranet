package de.kkendzia.myintranet.ei.core.utils;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.Objects.requireNonNull;

public interface Result<T>
{
    default boolean isSuccess()
    {
        return !hasFailures();
    }
    default boolean hasFailures()
    {
        return !getFailures().isEmpty();
    }
    List<Failure> getFailures();
    T getData();
    default Optional<T> getOptionalData()
    {
        return Optional.ofNullable(getData());
    }

    static <T> Result<T> success(T data)
    {
        return new DefaultResult<>(data, emptyList());
    }
    static Result<Void> success()
    {
        return new DefaultResult<>(null, emptyList());
    }
    static <T> Result<T> failure(Failure failure)
    {
        requireNonNull(failure, "failure can't be null!");
        return new DefaultResult<>(null, List.of(failure));
    }
    static <T> Result<T> failures(List<Failure> failures)
    {
        requireNonNull(failures, "failures can't be null!");
        return new DefaultResult<>(null, failures);
    }

    class DefaultResult<T> implements Result<T>
    {
        private final T data;
        private final List<Failure> failures;

        public DefaultResult(T data, List<Failure> failures)
        {
            this.data = data;
            this.failures = requireNonNull(failures, "failures can't be null!");
        }

        @Override
        public T getData()
        {
            return data;
        }

        @Override
        public List<Failure> getFailures()
        {
            return failures;
        }
    }

    interface Failure{

    }
}
