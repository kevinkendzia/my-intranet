package de.kkendzia.myintranet.domain._core;

public interface HasMutableId<I extends ID> extends HasId<I>
{
    void setId(I id);
}
