package de.kkendzia.myintranet.ei.core.parameters;

import com.vaadin.flow.function.SerializableFunction;
import com.vaadin.flow.router.QueryParameters;

import static com.vaadin.flow.function.SerializableFunction.identity;

public class ParameterDefinition<T>
{
    private String name;
    private SerializableFunction<String, T> parser;

    public ParameterDefinition(
            String name,
            SerializableFunction<String, T> parser)
    {
        this.name = name;
        this.parser = parser;
    }

    public QueryParameters createQueryParameters(String value)
    {
        return QueryParameters.of(name, value);
    }

    //region SETTER / GETTER
    public String getName()
    {
        return name;
    }

    public SerializableFunction<String, T> getParser()
    {
        return parser;
    }
    //endregion

    //region STATIC FACTORIES
    public static ParameterDefinition<Long> longParam(String name)
    {
        return new ParameterDefinition<>(name, Long::parseLong);
    }

    public static ParameterDefinition<String> stringParam(String name)
    {
        return new ParameterDefinition<>(name, identity());
    }

    public static ParameterDefinition<Boolean> booleanParam(String name)
    {
        return new ParameterDefinition<>(name, s -> s == null || s.isEmpty() || Boolean.parseBoolean(s));
    }
    //endregion
}
