package de.kkendzia.myintranet.domain._core;

import java.util.UUID;

import static java.util.Objects.requireNonNull;

public abstract class AbstractID implements ID
{
    private final UUID value;

    protected AbstractID(final UUID value)
    {
        this.value = requireNonNull(value, "value can't be null!");
    }

    protected AbstractID(final String value)
    {
        this(UUID.fromString(requireNonNull(value, "value can't be null!")));
    }

    protected AbstractID()
    {
        this(UUID.randomUUID());
    }

    public UUID getValue()
    {
        return value;
    }
}
