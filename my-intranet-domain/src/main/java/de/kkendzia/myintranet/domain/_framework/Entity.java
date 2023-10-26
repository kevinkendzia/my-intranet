package de.kkendzia.myintranet.domain._framework;

import java.util.Objects;

public interface Entity extends HasId
{
    long NEW_ID = 0L;

    default boolean isNew()
    {
        return Objects.equals(getId(), Entity.NEW_ID);
    }
}
