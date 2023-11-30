package de.kkendzia.myintranet.ei.ui.components.navigation;

import java.io.Serializable;

@FunctionalInterface
public interface IdNavigationAction<I> extends Serializable
{
    void execute(I id);
}
