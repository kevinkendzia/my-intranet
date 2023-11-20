package de.kkendzia.myintranet.domain._core;

public interface HasId<I extends ID>
{
    I getId();

    void setId(I id);
}
