package de.kkendzia.myintranet.ei.ui._framework.parameters;

import com.vaadin.flow.function.SerializableFunction;

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
    //endregion
}
