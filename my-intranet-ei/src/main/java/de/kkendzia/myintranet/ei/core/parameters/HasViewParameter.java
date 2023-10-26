package de.kkendzia.myintranet.ei.core.parameters;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.RouteConfiguration;

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
        setViewParameter(this, value);
    }

    static <T, C extends Component & HasUrlParameter<T>> void setViewParameter(HasViewParameter<T> source, T value)
    {
        //noinspection unchecked
        String deepLinkingUrl = RouteConfiguration.forSessionScope().getUrl((Class<C>)source.getClass(), value);
        ((Component)source)
                .getUI()
                .orElseThrow(() -> new IllegalArgumentException("UI is NOT accessible!"))
                .getPage()
                .getHistory()
                .replaceState(null, deepLinkingUrl);
    }
}
