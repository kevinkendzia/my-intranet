package de.kkendzia.myintranet.ei.core.navigation;

import java.io.Serializable;

@FunctionalInterface
public interface NavigationAction extends Serializable
{
    void execute();
}
