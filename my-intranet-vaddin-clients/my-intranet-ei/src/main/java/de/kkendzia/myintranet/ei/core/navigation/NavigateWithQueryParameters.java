package de.kkendzia.myintranet.ei.core.navigation;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.QueryParameters;

import java.io.Serializable;

import static org.slf4j.LoggerFactory.getLogger;

@FunctionalInterface
public interface NavigateWithQueryParameters extends Serializable
{
    void execute(QueryParameters queryParameters);

    //region STATIC
    static NavigateWithQueryParameters to(Class<? extends Component> target)
    {
        return qp ->
        {
            if (qp != null)
            {
                UI.getCurrent().navigate(target, qp);
            }
            else
            {
                getLogger(NavigateWithQueryParameters.class).debug("No QueryParameters provided, won't navigate!");
            }
        };
    }
    //endregion
}
