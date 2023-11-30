package de.kkendzia.myintranet.ei.ui.components.navigation;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.function.SerializableFunction;
import com.vaadin.flow.router.HasUrlParameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.util.Objects.requireNonNull;

public class NavigateWithItem<T, C extends Component & HasUrlParameter<I>, I> implements ItemNavigationAction<T>
{
    private static final Logger LOGGER = LoggerFactory.getLogger(NavigateWithItem.class);

    private final Class<C> target;
    private final SerializableFunction<T, I> idProvider;

    public NavigateWithItem(
            Class<C> target,
            SerializableFunction<T, I> idProvider)
    {
        this.target = requireNonNull(target, "target can't be null!");
        this.idProvider = requireNonNull(idProvider, "idProvider can't be null!");
    }

    @Override
    public void execute(T item)
    {
        I id = idProvider.apply(item);
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
