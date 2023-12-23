package de.kkendzia.myintranet.ei._framework.parameters;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;

import static de.kkendzia.myintranet.ei.utils.HistoryUtils.updateUrl;

public interface HasViewParameter<T> extends HasElement, HasUrlParameter<T>
{
    String KEY_TYPED_PARAMETER = "__TYPED_PARAMETER__";

    @Override
    default void setParameter(
            BeforeEvent event,
            T parameter)
    {
        ComponentUtil.setData((Component) this, KEY_TYPED_PARAMETER, parameter);
    }

    default T getViewParameter()
    {
        //noinspection unchecked
        return (T) ComponentUtil.getData((Component) this, KEY_TYPED_PARAMETER);
    }

    default void setViewParameter(T value)
    {
        updateUrl(this, value);
    }
}
