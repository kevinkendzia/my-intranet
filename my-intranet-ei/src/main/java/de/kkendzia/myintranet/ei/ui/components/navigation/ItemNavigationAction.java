package de.kkendzia.myintranet.ei.ui.components.navigation;

import java.io.Serializable;

@FunctionalInterface
public interface ItemNavigationAction<T> extends Serializable
{
    void execute(T item);
}
