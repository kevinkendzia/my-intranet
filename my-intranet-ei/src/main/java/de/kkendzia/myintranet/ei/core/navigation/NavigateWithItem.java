package de.kkendzia.myintranet.ei.core.navigation;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.function.SerializableFunction;
import com.vaadin.flow.router.HasUrlParameter;

import java.io.Serializable;

import static org.slf4j.LoggerFactory.getLogger;

@FunctionalInterface
public interface NavigateWithItem<T> extends Serializable
{
    void execute(T item);

    //region STATIC
    static <T, C extends Component & HasUrlParameter<I>, I> NavigateWithItem<T> to(
            Class<C> target,
            final SerializableFunction<T, I> idProvider)
    {
        return item ->
        {
            I id = idProvider.apply(item);
            if (id != null)
            {
                UI.getCurrent().navigate(target, id);
            }
            else
            {
                getLogger(NavigateWithItem.class).debug("No item provided, won't navigate!");
            }
        };
    }
    //endregion
}
