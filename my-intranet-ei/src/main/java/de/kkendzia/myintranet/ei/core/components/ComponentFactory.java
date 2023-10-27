package de.kkendzia.myintranet.ei.core.components;

import com.vaadin.flow.component.Component;

import java.io.Serializable;

public interface ComponentFactory<T> extends Serializable
{
    Component create(T source);
}
