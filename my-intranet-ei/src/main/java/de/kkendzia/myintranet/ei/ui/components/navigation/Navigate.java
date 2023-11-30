package de.kkendzia.myintranet.ei.ui.components.navigation;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.util.Objects.requireNonNull;

public class Navigate<C extends Component> implements NavigationAction
{
    private static final Logger LOGGER = LoggerFactory.getLogger(Navigate.class);

    private final Class<C> target;

    public Navigate(Class<C> target)
    {
        this.target = requireNonNull(target, "target can't be null!");
    }

    @Override
    public void execute()
    {
        UI.getCurrent().navigate(target);
    }
}
