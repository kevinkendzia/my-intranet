package de.kkendzia.myintranet.ei.core.navigation;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.HasUrlParameter;

import java.io.Serializable;

import static org.slf4j.LoggerFactory.getLogger;

@FunctionalInterface
public interface NavigateWithId<I> extends Serializable
{
    void execute(I id);

    //region STATIC
    static <I, C extends Component & HasUrlParameter<I>> NavigateWithId<I> to(Class<C> target)
    {
        return id ->
        {
            if (id != null)
            {
                UI.getCurrent().navigate(target, id);
            }
            else
            {
                getLogger(NavigateWithId.class).debug("No id provided, won't navigate!");
            }
        };
    }
    //endregion
}
