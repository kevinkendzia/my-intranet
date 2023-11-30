package de.kkendzia.myintranet.ei.ui.components.navigation;

import java.io.Serializable;

@FunctionalInterface
public interface NavigationAction extends Serializable
{
    void execute();
}
