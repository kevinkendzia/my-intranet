package de.kkendzia.myintranet.ei.core.navigation;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.HasUrlParameter;

import java.io.Serializable;

import static org.slf4j.LoggerFactory.getLogger;

@FunctionalInterface
public interface Navigate extends Serializable
{
    void execute();

    //region STATIC
    static Navigate to(Class<? extends Component> target)
    {
        return () -> UI.getCurrent().navigate(target);
    }

    static <I, C extends Component & HasUrlParameter<I>> Navigate to(Class<C> target, I id)
    {
        return () ->
        {
            if (id != null)
            {
                UI.getCurrent().navigate(target, id);
            }
            else
            {
                getLogger(Navigate.class).debug("No id provided, won't navigate!");
            }
        };
    }
    //endregion
}
