package de.kkendzia.myintranet.ei.core.view.layouts;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.function.SerializableFunction;
import com.vaadin.flow.router.HasUrlParameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.util.Objects.requireNonNull;

public class SearchLayout<T> extends Composite<VerticalLayout>
{
    private final Span spSearchText = new Span();
    private final Grid<T> grid = new Grid<>();

    private NavigationAction<T> navigationAction;

    public SearchLayout()
    {
        grid.addItemClickListener(e ->
        {
            if (navigationAction != null)
            {
                navigationAction.execute(e.getItem());
            }
        });

        VerticalLayout root = getContent();
        root.setHeightFull();
        root.add(new H3("AH SEARCH"));
        root.add(new HorizontalLayout(new Span("Searchtext:"), spSearchText));
        root.addAndExpand(grid);
    }

    public void setNavigationAction(NavigationAction<T> navigationAction)
    {
        this.navigationAction = navigationAction;
    }

    public Grid<T> getGrid()
    {
        return grid;
    }

    public void setSearchText(String searchtext)
    {
        spSearchText.setText(searchtext);
    }

    @FunctionalInterface
    public interface NavigationAction<T>
    {
        void execute(T item);

        class NavigateWithId<T, C extends Component & HasUrlParameter<Long>> implements NavigationAction<T>
        {
            private static final Logger LOGGER = LoggerFactory.getLogger(NavigateWithId.class);

            private Class<C> target;
            private SerializableFunction<T, Long> idProvider;

            public NavigateWithId(
                    Class<C> target,
                    SerializableFunction<T, Long> idProvider)
            {
                this.target = requireNonNull(target, "target can't be null!");
                this.idProvider = requireNonNull(idProvider, "idProvider can't be null!");
            }

            @Override
            public void execute(T item)
            {
                Long id = idProvider.apply(item);
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
    }
}
