package de.kkendzia.myintranet.ei.core.parameters;

import com.vaadin.flow.function.SerializableFunction;
import com.vaadin.flow.router.QueryParameters;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.vaadin.flow.function.SerializableFunction.identity;

public record ParameterDefinition<T>(
        String name,
        SerializableFunction<String, T> parser) implements Serializable
{
    public QueryParameters createQueryParameters(String value)
    {
        return QueryParameters.of(name, value);
    }

    //region STATIC FACTORIES
    public static ParameterDefinition<Long> longParam(String name)
    {
        return new ParameterDefinition<>(name, Long::parseLong);
    }

    public static ParameterDefinition<Integer> intParam(String name)
    {
        return new ParameterDefinition<>(name, Integer::parseInt);
    }

    public static ParameterDefinition<String> stringParam(String name)
    {
        return new ParameterDefinition<>(name, identity());
    }

    public static ParameterDefinition<Boolean> booleanParam(String name)
    {
        return new ParameterDefinition<>(name, s -> s == null || s.isEmpty() || Boolean.parseBoolean(s));
    }

    public static ParameterDefinition<LocalDate> localDateParam(String name, String pattern)
    {
        return new ParameterDefinition<>(name, x -> LocalDate.parse(x, DateTimeFormatter.ofPattern(pattern)));
    }
//endregion
}
