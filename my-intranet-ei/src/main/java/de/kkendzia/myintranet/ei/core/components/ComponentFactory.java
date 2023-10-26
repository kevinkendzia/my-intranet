package de.kkendzia.myintranet.ei.core.components;

import com.vaadin.flow.component.Component;

public interface ComponentFactory<T>
{
    Component create(T source);
}
