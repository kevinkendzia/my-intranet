package de.kkendzia.myintranet.ei.core.navigation;

import java.io.Serializable;

@FunctionalInterface
public interface IdNavigationAction<I> extends Serializable
{
    void execute(I id);
}
