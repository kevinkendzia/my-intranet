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

    // STATIC

    public static ParameterDefinition<Long> longParam(String name)
    {
        return new ParameterDefinition<>(name);
    }
}
