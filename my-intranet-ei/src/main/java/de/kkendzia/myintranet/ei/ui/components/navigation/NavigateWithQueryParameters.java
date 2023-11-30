package de.kkendzia.myintranet.ei.ui.components.navigation;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.QueryParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.util.Objects.requireNonNull;

public class NavigateWithQueryParameters<C extends Component> implements QueryParametersNavigationAction
{
    private static final Logger LOGGER = LoggerFactory.getLogger(NavigateWithQueryParameters.class);

    private final Class<C> target;

    public NavigateWithQueryParameters(Class<C> target)
    {
        this.target = requireNonNull(target, "target can't be null!");
    }

    @Override
    public void execute(QueryParameters queryParameters)
    {
        if (queryParameters != null)
        {
            UI.getCurrent().navigate(target, queryParameters);
        }
        else
        {
            LOGGER.debug("No queryParameters provided, won't navigate!");
        }
    }
}
