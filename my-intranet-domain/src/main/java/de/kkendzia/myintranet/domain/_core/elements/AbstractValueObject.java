package de.kkendzia.myintranet.domain._core.elements;

/**
 * Base class for ValueObjects to enforce overrides for equals() & hashCode().
 */
public abstract class AbstractValueObject implements ValueObject
{
    @Override
    public abstract boolean equals(Object other);

    @Override
    public abstract int hashCode();
}
