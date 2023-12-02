package de.kkendzia.myintranet.domain._core;

import java.util.Objects;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

public abstract class AbstractID extends AbstractValueObject implements ID
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

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }
        final AbstractID that = (AbstractID) o;
        return Objects.equals(getValue(), that.getValue());
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(getValue());
    }

    @Override
    public String toString()
    {
        return String.valueOf(value);
    }
}
