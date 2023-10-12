package de.kkendzia.myintranet.ei.ui._framework.parameters;

public class ParameterDefinition<T>
{
    private String name;

    public ParameterDefinition(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    //region STATIC FACTORIES
    public static ParameterDefinition<Long> longParam(String name)
    {
        return new ParameterDefinition<>(name);
    }
    public static ParameterDefinition<String> stringParam(String name)
    {
        return new ParameterDefinition<>(name);
    }
    //endregion
}
