package de.kkendzia.myintranet.ei.core.view;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.HasStyle;

public interface EIView extends HasElement, HasStyle
{
    default <T> T getViewData(Class<T> key)
    {
        return ComponentUtil.getData((Component) this, key);
    }
    default <T> void setViewData(Class<T> key, T value)
    {
        ComponentUtil.setData((Component) this, key, value);
    }
}
