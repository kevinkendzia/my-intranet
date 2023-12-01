package de.kkendzia.myintranet.ei.core.navigation;

import java.io.Serializable;

@FunctionalInterface
public interface ItemNavigationAction<T> extends Serializable
{
    void execute(T item);
}
