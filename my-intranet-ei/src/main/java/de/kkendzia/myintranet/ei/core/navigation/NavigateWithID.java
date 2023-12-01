package de.kkendzia.myintranet.ei.core.navigation;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.HasUrlParameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.util.Objects.requireNonNull;

public class NavigateWithID<I, C extends Component & HasUrlParameter<I>> implements IdNavigationAction<I>
{
    private static final Logger LOGGER = LoggerFactory.getLogger(NavigateWithID.class);

    private final Class<C> target;

    public NavigateWithID(Class<C> target)
    {
        this.target = requireNonNull(target, "target can't be null!");
    }

    @Override
    public void execute(I id)
    {
        if (id != null)
        {
            UI.getCurrent().navigate(target, id);
        }
        else
        {
            LOGGER.debug("No id provided, won't navigate!");
        }
    }
}
